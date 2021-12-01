package com.telran.contacts.config;

import com.telran.contacts.dao.auth.UserRepository;
import com.telran.contacts.filter.AuthenticationFilter;
import com.telran.contacts.filter.AuthorizationFilter;
import com.telran.contacts.service.UserDetailsServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    Environment env;

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl(userRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors().disable()
                .headers().frameOptions().disable()
//                .and()
//                .httpBasic()
                .and()
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager(),env,userRepository))
//                .and()
                .authorizeRequests()
//                USER
                .mvcMatchers(HttpMethod.GET, "/contact/**").hasRole("USER")
                .mvcMatchers(HttpMethod.POST, "/contact/**").hasRole("USER")
                .mvcMatchers(HttpMethod.PUT, "/contact/**").hasRole("USER")
                .mvcMatchers(HttpMethod.DELETE, "/contact/**").hasRole("USER")
//                ADMIN
                .mvcMatchers(HttpMethod.DELETE,"/auth/remove/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET,"/auth/all").hasRole("ADMIN")
//                PERMIT_ALL
                .mvcMatchers(HttpMethod.POST,"/auth/registrations").permitAll()
                .mvcMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest()
                .denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @SneakyThrows
    private AuthenticationFilter getAuthenticationFilter(){
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(),env);
        authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
        return authenticationFilter;
    }

}

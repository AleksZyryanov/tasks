package com.telran.contacts.filter;

import com.telran.contacts.dao.auth.UserRepository;
import com.telran.contacts.pojo.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    Environment env;
    UserRepository userRepository;

    public AuthorizationFilter(AuthenticationManager authenticationManager, Environment env, UserRepository userRepository) {
        super(authenticationManager);
        this.env = env;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String authorizationHeader = request.getHeader(env.getProperty("authorization.token.header.name"));
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith(env.getProperty("authorization.token.header.prefix"))){
            chain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authorizationHeader){
        String token = authorizationHeader.replace(env.getProperty("authorization.token.header.prefix"),"");

        String secretKey = env.getProperty("token.secret");

        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String username = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if (username == null){
            return null;
        }
        UserEntity entity = userRepository.getUserByUserName(username);
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                AuthorityUtils.createAuthorityList(entity.getRoles().toArray(String[]::new))
        );
    }
}

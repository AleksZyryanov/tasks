package com.telran.contacts.config;

import com.telran.contacts.pojo.dto.ErrorDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
//@EnableSwagger2       /*Annotation needed for Swagger v2.x, not needed for 3.x*/
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Collections.singletonList(basicSecurityContext()))
                .securitySchemes(Collections.singletonList(new BasicAuth("Basic")))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.telran"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .useDefaultResponseMessages(false)
                .genericModelSubstitutes(ErrorDto.class, ResponseEntity.class);
    }



    private SecurityContext basicSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(
                        new SecurityReference("Basic",
                                new AuthorizationScope[0])))
                .build();
    }



    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("TelRan Contact-App API")
                .description("My Test Description")
                .version("0.0.1")
                .license("some licence version 1.0")
                .licenseUrl("https://some.license.url")
                .termsOfServiceUrl("https://some.service.url")
                .build();
    }
}

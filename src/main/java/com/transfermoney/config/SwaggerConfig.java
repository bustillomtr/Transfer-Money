package com.transfermoney.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Configuration class for Swagger.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Docket is needed to be defined as swagger mainly centers around it
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.transfermoney.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(customApiInfo());
    }

    /**
     * Information about the API.
     *
     * @return
     */
    private ApiInfo customApiInfo() {
        return new ApiInfo(
                "Transfer Money API",
                "This is not a real world application.",
                "v1.0",
                "",
                new Contact("Mark Tristan Bustillo", "", ""),
                "", "", Collections.emptyList()
        );
    }

}

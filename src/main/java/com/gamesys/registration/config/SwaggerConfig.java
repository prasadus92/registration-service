package com.gamesys.registration.config;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket registrationApi(BuildProperties buildProperties) {
        String descriptionFormat = "Documentation Registration Service API v%s";
        String version = buildProperties.getVersion();
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.gamesys.registration"))
            .paths(PathSelectors.ant("/api/v1/**"))
            .build()
            .apiInfo(new ApiInfoBuilder().version(version).title("Registration Service API").description(String.format(descriptionFormat, version)).build());
    }
}

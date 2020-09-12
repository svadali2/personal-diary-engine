package com.diary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select().build();
    }

    private Predicate<String> paths() {
        return regex("/api/swaggertest.*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Personal Diary Engine API")
                .description("Personal Diary Engine API reference for developers")
                .termsOfServiceUrl("http://personaldiaryengine.com")
                .licenseUrl("test@gmail.com").version("1.0").build();
    }

}

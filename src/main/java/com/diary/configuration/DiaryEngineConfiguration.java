package com.diary.configuration;

import com.diary.service.AuthService;
import com.diary.service.ValidatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DiaryEngineConfiguration {
    @Bean
    public AuthService authService() {
        return new AuthService();
    }

    @Bean
    public ValidatorService formatterService() {
        return new ValidatorService();
    }
}

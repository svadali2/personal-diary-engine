package com.diary.configuration;

import com.diary.service.AuthService;
import com.diary.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DiaryEngineConfiguration {
    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public AuthService authService() {
        return new AuthService();
    }

}

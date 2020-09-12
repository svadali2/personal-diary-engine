package com.diary.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Configs for the respective mongo beans
 */
@Configuration
public class MongoConfiguration {
    @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://admin:admin@cluster0.9bskj.mongodb.net/DiaryDetails");
    }
}

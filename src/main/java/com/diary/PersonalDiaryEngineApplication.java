package com.diary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Slf4j
@EnableMongoRepositories("com.diary.db")
public class PersonalDiaryEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalDiaryEngineApplication.class, args);
    }

}

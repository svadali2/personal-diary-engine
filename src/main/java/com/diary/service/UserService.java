package com.diary.service;

import com.diary.db.UserRepository;
import com.diary.model.User;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;

@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean save(User user) {
        try {
            MongoCollection<Document> users = mongoTemplate.getCollection("Users");
            users.insertOne(Document.parse(new Gson().toJson(user)));
        } catch (Exception e) {
            log.error("Failed to insert data to applications");
            return false;
        }
        return true;
    }

    public boolean doesUserExist(User user) {
        return userRepository.exists(Example.of(user));
    }

}
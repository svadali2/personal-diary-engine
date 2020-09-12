package com.diary.service;

import com.diary.db.UserRepository;
import com.diary.model.User;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserService userService;

    @Mock
    UserRepository userRepository;
    @Mock
    MongoTemplate mongoTemplate;
    @Mock
    MongoCollection users;
    User user;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        mongoTemplate = Mockito.mock(MongoTemplate.class);
        users = Mockito.mock(MongoCollection.class);
        userService = new UserService(userRepository, mongoTemplate);
        user = new User();
    }

    @Test
    public void shouldReturnTrueFiSaveSuccessful() {
        when(mongoTemplate.getCollection("Users")).thenReturn(users);
        doNothing().when(users).insertOne(any());
        assertTrue(userService.save(user));
    }

    @Test
    public void shouldReturnFalseIfSaveFails() {
        when(mongoTemplate.getCollection("Users")).thenReturn(users);
        doThrow(MongoWriteException.class).when(users).insertOne(any(Document.class));
        assertFalse(userService.save(user));
    }

    @Test
    public void shouldReturnTrueIfUserExists() {
        when(userRepository.exists(any())).thenReturn(true);
        assertTrue(userService.doesUserExist(user));
    }

    @Test
    public void shouldReturnFalseIfUserDoesNotExist() {
        when(userRepository.exists(any())).thenReturn(false);
        assertFalse(userService.doesUserExist(user));
    }
}
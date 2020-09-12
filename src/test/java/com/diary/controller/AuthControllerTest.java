package com.diary.controller;

import com.diary.model.User;
import com.diary.service.AuthService;
import com.diary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {
    AuthController authController;

    @Mock
    AuthService authService;
    @Mock
    UserService userService;
    User user1;
    User user2;

    @BeforeEach
    public void setUp() {
        authService = Mockito.mock(AuthService.class);
        userService = Mockito.mock(UserService.class);
        authController = new AuthController(authService, userService);
        user1 = new User();
        user2 = new User();
        user1.setUsername("admin");
        user2.setUsername("pass");
        authController.createAuthenticationToken(user1);
    }

    @Test
    public void shouldReturn400IfUserExists() {
        when(userService.doesUserExist(any())).thenReturn(true);
        assertEquals(authController.createAuthenticationToken(user1).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturn200IfUserIsNew() {
        when(userService.doesUserExist(any())).thenReturn(false);
        assertEquals(authController.createAuthenticationToken(user1).getStatusCode(), HttpStatus.OK);
    }
}
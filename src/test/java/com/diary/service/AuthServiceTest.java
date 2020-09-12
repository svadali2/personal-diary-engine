package com.diary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.Assert.assertEquals;


class AuthServiceTest {

    AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
    }

    @Test
    public void shouldGenerateTokenForGivenUsername() {
        assertEquals(String.class, authService.generateToken(randomAlphanumeric(10)).getClass());
    }
}
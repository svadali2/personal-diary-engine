package com.diary.service;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;

import static com.diary.service.ValidatorService.validateToken;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorServiceTest {

    private final ValidatorService validatorService = new ValidatorService();

    @Test
    public void shouldReturnTrueIfDateIsValid() {
        assertTrue(validatorService.isValidDate("10-10-2020 20:10:34"));
        assertTrue(validatorService.isValidDate("10-12-3020 20:10:34"));
        assertTrue(validatorService.isValidDate("01-9-1920 20:10:34"));
        assertTrue(validatorService.isValidDate("10-10-1020 20:10:34"));
    }

    @Test
    public void shouldReturnFalseIfDateIsInvalid() {
        assertFalse(validatorService.isValidDate("10-13-2020 20:10:34"));
        assertFalse(validatorService.isValidDate("10-10-2020 20:"));
        assertFalse(validatorService.isValidDate("10-10-20:10:34"));
        assertFalse(validatorService.isValidDate("10-10-"));
        assertFalse(validatorService.isValidDate("10-10-2020 "));
    }

    @Test
    public void shouldDoNothingIfTokenIsValid() {
        validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzb3VtaXRyaSIsImV4cCI6MTU5OTkzODg0MiwiaWF0IjoxNTk5OTIwODQyfQ.QfM8QMHnwZrSkimuygSLQSw6XyMQTjYuWsebW3B6yfR45oEoBo79yHKJkT6s1wgsIPgSXKan_e1XeBhwn5tOkQ\n");
    }

    @Test
    public void shouldThrowIfTokenIsInvalid() {
        assertThrows(MalformedJwtException.class,
                ()->{
                    validateToken("notaToken");
                });
    }

}
package com.diary.controller;


import com.diary.model.Entry;
import com.diary.service.AuthService;
import com.diary.service.EntryService;
import exception.EntryDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class DiaryEntryControllerTest {

    DiaryEntryController diaryEntryController;

    private static final String validToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzb3VtaXRyaSIsImV4cCI6MTU5OTkzODg0MiwiaWF0IjoxNTk5OTIwODQyfQ.QfM8QMHnwZrSkimuygSLQSw6XyMQTjYuWsebW3B6yfR45oEoBo79yHKJkT6s1wgsIPgSXKan_e1XeBhwn5tOkQ\n";

    @Mock
    EntryService entryService;
    @Mock
    AuthService authService;
    Entry entry2;
    String username1 = randomAlphabetic(10);

    @BeforeEach
    public void setUp() throws MalformedURLException {
        entryService = Mockito.mock(EntryService.class);
        authService = Mockito.mock(AuthService.class);
        diaryEntryController = new DiaryEntryController(entryService, authService);
        entry2 = new Entry();
        entry2.setId(1234567);
        entry2.setLinks(Arrays.asList(new URL("https://www.google.com")));
        entry2.setDateTime(randomAlphanumeric(10));
        entry2.setTheme(randomAlphabetic(10));
        entry2.setWords(randomAlphabetic(20));
        when(authService.getUsernameFromToken(anyString())).thenReturn(username1);
    }

    @Test
    public void shouldCreateEntryIfEntryIsValid() {
        when(entryService.saveDiaryEntry(any())).thenReturn(Optional.of(123456));
        ResponseEntity<?> expected = diaryEntryController.createEntry(validToken, entry2);
        assertEquals(expected.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturn500IfEntryIsInvalid() {
        when(entryService.saveDiaryEntry(any())).thenReturn(Optional.of(0));
        ResponseEntity<?> expected = diaryEntryController.createEntry(validToken, entry2);
        assertEquals(expected.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void shouldReturn200IfEntryIsRetrieved() throws EntryDoesNotExistException {
        when(entryService.getEntry(123, username1)).thenReturn(Optional.of(entry2));
        ResponseEntity<?> expected = diaryEntryController.getEntry(validToken, 123);
        assertEquals(expected.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturn404IfEntryIsNotRetrieved() throws EntryDoesNotExistException {
        when(entryService.getEntry(123, username1)).thenReturn(Optional.empty());
        ResponseEntity<?> expected = diaryEntryController.getEntry(validToken, 123);
        assertEquals(expected.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturn200IfLinksAreUpdated() throws EntryDoesNotExistException {
        when(entryService.updateLinks(123, entry2.getLinks(), username1)).thenReturn(Optional.of(123));
        ResponseEntity<?> expected = diaryEntryController.updateLinks(validToken, 123, entry2.getLinks());
        assertEquals(expected.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturn500IfLinksAreNotUpdated() throws EntryDoesNotExistException {
        ResponseEntity<?> expected = diaryEntryController.updateLinks(validToken, 123, entry2.getLinks());
        assertEquals(expected.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
package com.diary.service;


import com.diary.db.EntryRepository;
import com.diary.model.Entry;
import exception.EntryDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class EntryServiceTest {

    EntryService entryService;
    @Mock
    EntryRepository entryRepository;
    @Mock
    ValidatorService validatorService;
    @Mock
    AuthService authService;

    String username1 = randomAlphabetic(10);
    Entry entry1;
    Entry entry2;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        entryRepository = Mockito.mock(EntryRepository.class);
        validatorService = Mockito.mock(ValidatorService.class);
        authService = Mockito.mock(AuthService.class);
        entryService = new EntryService(entryRepository, validatorService);
        entry1 = new Entry();
        entry2 = new Entry();
        entry1.setId(0);
        entry1.setLinks(new ArrayList<>());
        entry1.setDateTime(randomAlphanumeric(10));
        entry1.setTheme(randomAlphabetic(10));
        entry1.setWords(randomAlphabetic(20));

        entry2.setId(1234567);
        entry2.setLinks(Arrays.asList(new URL("https://www.google.com")));
        entry2.setDateTime(randomAlphanumeric(10));
        entry2.setTheme(randomAlphabetic(10));
        entry2.setWords(randomAlphabetic(20));
        entry2.setUsername(username1);
    }

    @Test
    public void shouldSaveDiaryEntryIfEntryIsValid() {
        when(entryService.doesEntryExist(entry1)).thenReturn(false);
        when(validatorService.isValidDate(anyString())).thenReturn(true);
        when(entryRepository.save(entry1)).thenReturn(entry2);
        Optional<Integer> expected = entryService.saveDiaryEntry(entry1);
        assertTrue(expected.isPresent());
        assertNotEquals(expected.get(), entry1.getId());
        assertEquals(expected.get(), entry2.getId());
    }

    @Test
    public void shouldReturnEmptyOptionalIfEntryExists() {
        when(entryService.doesEntryExist(entry1)).thenReturn(true);
        Optional<Integer> expected = entryService.saveDiaryEntry(entry1);
        assertFalse(expected.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalIfDateTimeIsInvalid() {
        when(entryService.doesEntryExist(entry1)).thenReturn(false);
        when(validatorService.isValidDate(anyString())).thenReturn(false);
        Optional<Integer> expected = entryService.saveDiaryEntry(entry1);
        assertFalse(expected.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalIfEntryIsNull() {
        when(entryService.doesEntryExist(entry1)).thenReturn(false);
        when(validatorService.isValidDate(anyString())).thenReturn(true);
        when(entryRepository.save(entry1)).thenThrow(IllegalArgumentException.class);
        Optional<Integer> expected = entryService.saveDiaryEntry(entry1);
        assertFalse(expected.isPresent());
    }

    @Test
    public void shouldUpdateLinksIfIdIsOk() throws EntryDoesNotExistException {
        when(entryRepository.findById(any())).thenReturn(Optional.of(entry2));
        when(entryRepository.save(any())).thenReturn(entry2);
        Optional<Integer> expected = entryService.updateLinks(10, entry2.getLinks(), username1);
        assertEquals(expected.get(), entry2.getId());
    }

    @Test
    public void shouldThrowExceptionIfIdDoesNotExist() throws EntryDoesNotExistException {
        when(entryRepository.findById(entry1.getId())).thenReturn(Optional.empty());
        assertThrows(EntryDoesNotExistException.class, () -> {
                entryService.updateLinks(10, entry2.getLinks(), randomAlphabetic(10));
        });
    }

    @Test
    public void shouldGetEntry() {
        when(entryRepository.findById(any())).thenReturn(Optional.of(entry2));
        Optional<Entry> expected = entryService.getEntry(10, username1);
        assertEquals(expected.get(), entry2);
    }

    @Test
    public void shouldThrowExceptionIfEntryIsNotValid() {
        when(entryRepository.findById(any())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            entryService.getEntry(10, randomAlphabetic(10));
        });
    }

    @Test
    public void shouldReturnFalseIfEntryDoesNotExist() {
        when(entryRepository.exists(any())).thenReturn(false);
        assertFalse(entryService.doesEntryExist(entry1));
    }

    @Test
    public void shouldReturnTrueIfEntryExists() {
        when(entryRepository.exists(any())).thenReturn(true);
        assertTrue(entryService.doesEntryExist(entry1));
    }
}
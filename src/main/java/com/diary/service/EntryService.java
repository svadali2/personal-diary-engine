package com.diary.service;

import com.diary.db.EntryRepository;
import com.diary.model.Entry;
import exception.EntryDoesNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EntryService {

    @Autowired
    EntryRepository entryRepository;
    @Autowired
    ValidatorService validatorService;


    public Optional<Integer> saveDiaryEntry(Entry entry) {
        if (doesEntryExist(entry) || !validatorService.isValidDate(entry.getDateTime())) {
            log.error(String.format("Diary Entry exists already or Date is not formatted correctly - Please double check your input"));
            return Optional.empty();
        }
        entry.setId(Integer.parseInt(RandomStringUtils.randomNumeric(8)));
        try {
            return Optional.of(entryRepository.save(entry).getId());
        } catch (IllegalArgumentException e) {
            log.error(String.format("Diary Entry is null"));
            return Optional.empty();
        }
    }

    public Optional<Integer> updateLinks(int id, List<URL> links, String username) throws EntryDoesNotExistException {
        Entry current = entryRepository.findById(id).orElseThrow(() -> new EntryDoesNotExistException(id));
        if (!current.getUsername().equals(username)) {
            log.error("User not Authenticated.");
            return Optional.empty();
        }
        current.setLinks(links);
        return Optional.of(entryRepository.save(current).getId());
    }

    public Optional<Entry> getEntry(int id, String username) {
        Optional<Entry> retrievedEntry = entryRepository.findById(id);
        if (retrievedEntry.isPresent() && retrievedEntry.get().getUsername().equals(username)) {
            return retrievedEntry;
        }
        return Optional.empty();
    }

    public boolean doesEntryExist(Entry entry) {
        if (entryRepository.exists(Example.of(entry))) {
            log.error(String.format("Diary Entry exists already"));
            return true;
        }
        return false;
    }
}

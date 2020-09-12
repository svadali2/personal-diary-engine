package com.diary.service;

import com.diary.db.EntryRepository;
import com.diary.model.Entry;
import exception.EntryDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EntryService {

    @Autowired
    EntryRepository entryRepository;

    public Optional<Long> saveDiaryEntry(Entry entry) {
        try {
            if (entryRepository.exists(Example.of(entry))) {
                log.error(String.format("Diary Entry exists already"));
                return Optional.empty();
            }
            return Optional.of(entryRepository.save(entry).getId());
        } catch (IllegalArgumentException e) {
            log.error(String.format("Diary Entry is null"));
            return Optional.empty();
        }
    }

    public Optional<Long> updateLinks(long id, List<URL> links) throws EntryDoesNotExistException {
        Entry current = entryRepository.findById(id).orElseThrow(() -> new EntryDoesNotExistException(id));
        current.setLinks(links);
        return Optional.ofNullable(entryRepository.save(current).getId());
    }

    public Optional<Entry> getEntry(long id) {
        return entryRepository.findById(id);
    }
}

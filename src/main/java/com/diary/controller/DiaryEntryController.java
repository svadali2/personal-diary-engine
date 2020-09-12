package com.diary.controller;

import com.diary.model.Entry;
import com.diary.service.AuthService;
import com.diary.service.EntryService;
import exception.EntryDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.diary.service.AuthService.validateToken;


@RestController
@RequestMapping(value = "/api")
@Slf4j
public class DiaryEntryController {

    @Autowired
    EntryService entryService;
    @Autowired
    AuthService authService;

    @GetMapping("/swaggertest")
    public ResponseEntity<String> sayHello(
            @RequestHeader(name = "Authorization") String authenticationHeader
    ) {
        try {
            validateToken(authenticationHeader.replaceFirst("Header ", ""));
        } catch (Exception e) {
            log.error(String.format("Not Authenticated"));
            return ResponseEntity.status(401).body("Not Authenticated");
        }
        return ResponseEntity.ok().body("Welcome to the East Coast Plan");
    }

    @PostMapping("/entries")
    public ResponseEntity<?> createEntry(@RequestHeader(name = "Authorization") String authenticationHeader,
                                        @RequestBody Entry entry) {
        try {
            validateToken(authenticationHeader.replaceFirst("Header ", ""));
        } catch (Exception e) {
            log.error(String.format("Not Authenticated"));
            return ResponseEntity.status(401).body("Not Authenticated");
        }
        entry.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        long savedId = entryService.saveDiaryEntry(entry).orElse(0L);
        return savedId == 0L ?
                ResponseEntity.status(500).body("Something went wrong in the backend. Try again later.") :
                ResponseEntity.ok(savedId);
    }

    @PostMapping("/entries/{id}")
    public ResponseEntity<?> updateLinks(@RequestHeader(name = "Authorization") String authenticationHeader,
                            @PathVariable long id,
                            @Valid @RequestBody List<URL> links) throws EntryDoesNotExistException {

        try {
            validateToken(authenticationHeader.replaceFirst("Header ", ""));
        } catch (Exception e) {
            log.error(String.format("Not Authenticated"));
            return ResponseEntity.status(401).body("Not Authenticated");
        }
        long savedId = entryService.updateLinks(id, links).orElse(0L);
        return savedId == 0L ?
                ResponseEntity.status(500).body("Something went wrong in the backend. Try again later.") :
                ResponseEntity.ok(savedId);
    }

    @GetMapping("/entries/{id}")
    public ResponseEntity<?> getEntry(@RequestHeader(name = "Authorization") String authenticationHeader,
                                        @PathVariable long id) {
        try {
            validateToken(authenticationHeader.replaceFirst("Header ", ""));
        } catch (Exception e) {
            log.error(String.format("Not Authenticated"));
            return ResponseEntity.status(401).body("Not Authenticated");
        }
        Optional<Entry> retrievedEntry = entryService.getEntry(id);
        if (retrievedEntry.isPresent()) {
            return ResponseEntity.ok(retrievedEntry.get());
        }
        return ResponseEntity.badRequest().body("Entry does not Exist");
    }

}

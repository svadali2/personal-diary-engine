package com.diary.controller;

import com.diary.model.Entry;
import com.diary.service.AuthService;
import com.diary.service.EntryService;
import exception.EntryDoesNotExistException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ExampleProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

import static com.diary.service.ValidatorService.validateToken;


@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class DiaryEntryController {

    @Autowired
    private EntryService entryService;
    @Autowired
    private AuthService authService;

    @GetMapping("/swaggertest")
    public ResponseEntity<String> sayHello(
            @RequestHeader(name = "Authentication") String authenticationHeader
    ) {
        try {
            validateToken(authenticationHeader);
        } catch (Exception e) {
            log.error("Not Authenticated");
            return ResponseEntity.status(401).body("Not Authenticated");
        }
        return ResponseEntity.ok().body("Welcome to the East Coast Plan");
    }

    @PostMapping("/entries")
    public ResponseEntity<?> createEntry(@RequestHeader(name = "Authorization") String authenticationHeader,
                                        @RequestBody Entry entry) {
        try {
            validateToken(authenticationHeader);
        } catch (Exception e) {
            log.error("Not Authenticated");
            return ResponseEntity.status(401).body("Not Authenticated");
        }
        entry.setUsername(authService.getUsernameFromToken(authenticationHeader));
        int savedId = entryService.saveDiaryEntry(entry).orElse(0);
        return savedId == 0 ?
                ResponseEntity.status(500).body("Something went wrong in the backend. Try again later.") :
                ResponseEntity.ok(savedId);
    }

    @PostMapping("/entries/{id}")
    public ResponseEntity<?> updateLinks(@RequestHeader(name = "Authorization") String authenticationHeader,
                            @PathVariable int id,
                            @Valid @RequestBody List<URL> links) throws EntryDoesNotExistException {

        try {
            validateToken(authenticationHeader);
        } catch (Exception e) {
            log.error("Not Authenticated");
            return ResponseEntity.status(401).body("Not Authenticated");
        }
        int savedId = entryService.updateLinks(id, links, authService.getUsernameFromToken(authenticationHeader)).orElse(0);
        return savedId == 0 ?
                ResponseEntity.status(500).body("Something went wrong in the backend. Try again later.") :
                ResponseEntity.ok(savedId);
    }

    @GetMapping("/entries/{id}")
    public ResponseEntity<?> getEntry(@RequestHeader(name = "Authorization") String authenticationHeader,
                                        @PathVariable int id) {
        try {
            validateToken(authenticationHeader);
        } catch (Exception e) {
            log.error("Not Authenticated");
            return ResponseEntity.status(401).body("Not Authenticated");
        }
        Optional<Entry> retrievedEntry = entryService.getEntry(id, authService.getUsernameFromToken(authenticationHeader));
        if (retrievedEntry.isPresent()) {
            return ResponseEntity.ok(retrievedEntry.get());
        }
        return ResponseEntity.status(404).body("Entry does not exist.");
    }

}

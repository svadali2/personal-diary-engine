package com.diary.controller;

import com.diary.db.EntryRepository;
import com.diary.model.Entry;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class DiaryEngineController {

    @Autowired
    EntryRepository entryRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/api/swaggertest")
    public String sayHello() {
        return "Swagger Hello World 12 september";
    }

    @PostMapping("/api/entries")
    public Entry createEntry(@Valid @RequestBody Entry entry) {
        return entryRepository.save(entry);
    }

}

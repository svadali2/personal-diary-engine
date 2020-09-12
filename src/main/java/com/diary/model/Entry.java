package com.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.net.URL;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Document(collection = "Entries")
public class Entry {
    @Id
    private Long id;
    private String words;
    private String theme;
    private List<URL> links;
}

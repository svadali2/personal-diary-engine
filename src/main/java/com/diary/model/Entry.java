package com.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Document(collection = "Entries")
public class Entry {
    @Id
    @GeneratedValue
    private Long id;
    private String words;
    private SimpleDateFormat dateTime;
    private String theme;
    private List<URL> links;
}

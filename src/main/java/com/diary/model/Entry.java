package com.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
@Entity
@Document(collection = "Entries")
@NoArgsConstructor
public class Entry {
    @Id
    private int id;
    private String username;
    private String words;
    private String dateTime;
    private String theme;
    private List<URL> links;

    public Entry(String words, String dateTime, String theme, List<URL> links, String username) {
        this.words = words;
        this.dateTime = dateTime;
        this.theme = theme;
        this.links = links;
        this.username = username;
    }
}

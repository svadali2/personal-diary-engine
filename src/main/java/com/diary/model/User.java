package com.diary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Document(collection = "Users")
@Getter
@Setter
public class User {
    @Id
    private String username;
    @Column
    private String password;
}

package com.example.passwordmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "passwords")
@Data
@AllArgsConstructor
public class Password {

    @Id
    private String userId;

    private String password;

}

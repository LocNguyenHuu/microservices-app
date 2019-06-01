package com.example.permissions.entity;

import com.example.permissions.entity.model.UserRoleEnum;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_roles")
@RequiredArgsConstructor
@Data
public class UserRole {

    @Id
    private String userId;

    @NonNull
    private UserRoleEnum role;

}

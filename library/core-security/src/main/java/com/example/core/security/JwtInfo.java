package com.example.core.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

@Data
@AllArgsConstructor
public class JwtInfo implements Serializable {

    private String userId;

    private HashMap<String, Object> claims;

}

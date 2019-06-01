package com.example.permissions.entity.model;

public enum UserRoleEnum {

    USER, ADMIN;

    public static boolean contains(String s) {
        try {
            UserRoleEnum.valueOf(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

package com.courses.management.user;

public class UserAlreadyExistsExeption extends RuntimeException {
    public UserAlreadyExistsExeption(String message) {
        super(message);
    }
}

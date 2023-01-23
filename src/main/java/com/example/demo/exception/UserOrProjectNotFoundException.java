package com.example.demo.exception;

public class UserOrProjectNotFoundException extends RuntimeException {
    public UserOrProjectNotFoundException() { super("User or project is not found.");
    }
}

package com.telran.contacts.exceptions;

public class UserExistingException extends RuntimeException{
    public UserExistingException(String message) {
        super(message);
    }
}

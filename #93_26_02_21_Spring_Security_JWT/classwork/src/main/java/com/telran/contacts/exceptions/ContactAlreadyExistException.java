package com.telran.contacts.exceptions;

public class ContactAlreadyExistException extends RuntimeException{
    public ContactAlreadyExistException(String message) {
        super(message);
    }
}

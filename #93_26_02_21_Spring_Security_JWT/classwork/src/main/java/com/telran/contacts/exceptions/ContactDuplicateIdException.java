package com.telran.contacts.exceptions;

public class ContactDuplicateIdException extends RuntimeException {

    public ContactDuplicateIdException(String message) {
        super(message);
    }
}

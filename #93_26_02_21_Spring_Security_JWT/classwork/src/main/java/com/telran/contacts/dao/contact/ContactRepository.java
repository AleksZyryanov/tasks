package com.telran.contacts.dao.contact;

import com.telran.contacts.pojo.dto.ContactAddDto;
import com.telran.contacts.pojo.dto.ContactDto;

import java.util.List;
import java.util.UUID;

public interface ContactRepository {
    UUID addContact(ContactAddDto contact, String ownerEmail);
    List<ContactDto> getAllContacts(String ownerEmail);
    ContactDto getContactById(UUID id, String ownerEmail);
    void removeContact(UUID id, String ownerEmail);
    void updateContact(ContactDto contact, String ownerEmail);
    void removeAllContacts(String username);
}

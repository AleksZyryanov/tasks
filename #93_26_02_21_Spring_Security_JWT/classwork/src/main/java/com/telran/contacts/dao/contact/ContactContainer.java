package com.telran.contacts.dao.contact;

import com.telran.contacts.exceptions.ContactAlreadyExistException;
import com.telran.contacts.exceptions.ContactDuplicateIdException;
import com.telran.contacts.exceptions.ContactNotFoundException;
import com.telran.contacts.pojo.entity.ContactEntity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ContactContainer {
    String ownerId;
    final Map<UUID, ContactEntity> mapById;
    final Map<String,ContactEntity> mapByEmail;
    final Map<String,ContactEntity> mapByPhone;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public ContactContainer(String ownerId) {
        this.ownerId = ownerId;
        mapById = new ConcurrentHashMap<>();
        mapByEmail = new ConcurrentHashMap<>();
        mapByPhone = new ConcurrentHashMap<>();
    }

    public void addContact(ContactEntity entity){
        try{
            writeLock.lock();
            if(mapByEmail.containsKey(entity.getEmail())) throw new ContactAlreadyExistException("Contact with email: %s already exists".formatted(entity.getEmail()));
            if(mapByPhone.containsKey(entity.getPhone())) throw new ContactAlreadyExistException("Contact with phone: %s already exists".formatted(entity.getPhone()));
            if(mapById.putIfAbsent(entity.getId(),entity) != null){
                throw new ContactDuplicateIdException("Contact with id: %s already exists".formatted(entity.getId()));
            }
            mapByEmail.put(entity.getEmail(), entity);
            mapByPhone.put(entity.getPhone(), entity);
        }finally {
            writeLock.unlock();
        }
    }

    public void removeContact(UUID id){
        try {
            writeLock.lock();
            ContactEntity removed = mapById.remove(id);
            if (removed == null) {
                throw new ContactNotFoundException("Contact with id: %s not found".formatted(id));
            }
            mapByEmail.remove(removed.getEmail());
            mapByPhone.remove(removed.getPhone());
        } finally {
            writeLock.unlock();
        }
    }

    public ContactEntity getContactById(UUID id){
        try {
            readLock.lock();
            ContactEntity entity = mapById.get(id);
            if(entity == null){
                throw new ContactNotFoundException("Contact with id: %s not found".formatted(id));
            }
            return entity;
        } finally {
            readLock.unlock();
        }
    }

    public void updateContact(ContactEntity contact){
        try {
            writeLock.lock();
            ContactEntity removed = mapById.remove(contact.getId());
            if(removed == null){
                throw new ContactNotFoundException("Contact with id: %s not found".formatted(contact.getId()));
            }
            if(!removed.getEmail().equals(contact.getEmail()) && mapByEmail.containsKey(contact.getEmail())){
                mapById.put(removed.getId(),removed);
                throw new ContactAlreadyExistException("Contact with email: %s already exists".formatted(contact.getEmail()));
            }
            if(!removed.getPhone().equals(contact.getPhone()) && mapByPhone.containsKey(contact.getPhone())){
                mapById.put(removed.getId(),removed);
                throw new ContactAlreadyExistException("Contact with phone: %s already exists".formatted(contact.getPhone()));
            }
            mapByEmail.remove(removed.getEmail());
            mapByPhone.remove(removed.getPhone());
            mapByEmail.put(contact.getEmail(),contact);
            mapByPhone.put(contact.getPhone(),contact);
            mapById.put(contact.getId(),contact);
        } finally {
            writeLock.unlock();
        }
    }

    public List<ContactEntity> getAll(){
        try {
            readLock.lock();
            return new ArrayList<>(mapById.values());
        }finally {
            readLock.unlock();
        }
    }
}

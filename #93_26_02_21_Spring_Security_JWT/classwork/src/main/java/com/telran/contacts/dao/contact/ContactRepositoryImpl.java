package com.telran.contacts.dao.contact;

import com.telran.contacts.exceptions.ContactNotFoundException;
import com.telran.contacts.pojo.dto.ContactAddDto;
import com.telran.contacts.pojo.dto.ContactDto;
import com.telran.contacts.pojo.entity.ContactEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ContactRepositoryImpl implements ContactRepository {
    ModelMapper mapper;
    Map<String, ContactContainer> map = new ConcurrentHashMap<>();

    public ContactRepositoryImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UUID addContact(ContactAddDto contact, String ownerEmail) {
        ContactEntity entity = mapper.map(contact, ContactEntity.class);
        entity.setId(UUID.randomUUID());
        map.computeIfAbsent(ownerEmail, ContactContainer::new).addContact(entity);
        return entity.getId();
    }

    @Override
    public List<ContactDto> getAllContacts(String ownerEmail) {
        ContactContainer container = map.get(ownerEmail);
        if (container == null) {
            return List.of();
        }
        return container.getAll()
                .stream()
                .map(entity -> mapper.map(entity, ContactDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto getContactById(UUID id, String ownerEmail) {
        ContactContainer container = map.get(ownerEmail);
        if (container == null) {
            throw new ContactNotFoundException("Contact with id: %s not found".formatted(id));
        }
        return mapper.map(container.getContactById(id),ContactDto.class);
    }

    @Override
    public void removeContact(UUID id, String ownerEmail) {
        ContactContainer container = map.get(ownerEmail);
        if (container == null) {
            throw new ContactNotFoundException("Contact with id: %s not found".formatted(id));
        }
        container.removeContact(id);
    }

    @Override
    public void updateContact(ContactDto contact, String ownerEmail) {
        ContactContainer container = map.get(ownerEmail);
        if (container == null) {
            throw new ContactNotFoundException("Contact with id: %s not found".formatted(contact.getId()));
        }
        container.updateContact(mapper.map(contact,ContactEntity.class));
    }

    @Override
    public void removeAllContacts(String username) {
        map.remove(username);
    }
}

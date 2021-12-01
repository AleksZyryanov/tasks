package com.telran.contacts.dao.auth;

import com.telran.contacts.pojo.dto.UserDto;
import com.telran.contacts.pojo.entity.UserEntity;

import java.util.stream.Stream;

public interface UserRepository {
    void addUser(String email, String password);
    UserDto getUser(String username);
    void removeUser(String username);
    Stream<UserDto> getAll();
    UserEntity getUserByUserName(String username);
}

package com.telran.contacts.pojo.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    String email;
    String password;
    List<String> roles;
}

package com.telran.contacts.pojo.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    String email;
    List<String> roles;
}

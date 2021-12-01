package com.telran.contacts.pojo.entity;

import java.util.UUID;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContactEntity {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;
}

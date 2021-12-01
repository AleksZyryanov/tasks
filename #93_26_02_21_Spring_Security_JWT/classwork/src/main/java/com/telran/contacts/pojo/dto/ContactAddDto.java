package com.telran.contacts.pojo.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContactAddDto {
    @NotBlank
    @Pattern(regexp = "[A-Za-z]{1,15}", message = "The name should only consist of letters from 1-15.")
    String name;
    @NotBlank
    @Email
    String email;
    @NotBlank
    @Pattern(regexp = "^05\\d-\\d{7}$", message = "Phone should be in format 05x-xxxxxxx")
    String phone;
    @NotBlank
    @Size(max = 20)
    String address;
}

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
public class ContactDto {
    @NotBlank
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "ID Format Incorrect")
    String id;
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

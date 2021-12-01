package com.telran.contacts.pojo.dto;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorizationDto {
    @NotBlank
    @Email
    String email;
    @NotBlank
    @Size(min = 8, message = "At least 8 characters")
    String password;
}

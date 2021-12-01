package com.telran.contacts.pojo.dto;

import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDto<T> {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private T message;
    private String path;
}

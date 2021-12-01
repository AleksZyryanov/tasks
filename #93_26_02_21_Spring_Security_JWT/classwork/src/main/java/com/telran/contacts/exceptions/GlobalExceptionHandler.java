package com.telran.contacts.exceptions;

import com.telran.contacts.pojo.dto.ErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto<Map<String, String>> errorMessageDto = ErrorDto.<Map<String, String>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getBindingResult().getAllErrors()
                        .stream()
                        .collect(Collectors.toMap(e -> (
                                        (e instanceof FieldError) ? ((FieldError) e).getField() : e.getObjectName()),
                                DefaultMessageSourceResolvable::getDefaultMessage))
                )
                .build();
        return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDto<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return ErrorDto.<Map<String, String>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getConstraintViolations()
                        .stream()
                        .collect(Collectors.toMap(cv -> cv.getPropertyPath().toString().replaceAll("^.*\\.", ""),
                                ConstraintViolation::getMessage))
                )
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            ContactNotFoundException.class,
            ContactAlreadyExistException.class,
    })
    public ErrorDto<String> handleCustomException(RuntimeException ex, WebRequest request) {
        return ErrorDto.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {
            UserExistingException.class,
    })
    public ErrorDto<String> handleAuthorizedException(RuntimeException ex, WebRequest request) {
        return ErrorDto.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorDto<String> handleServerInternalException(RuntimeException ex, WebRequest request) {
        return ErrorDto.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message("Something went wrong, please contact to support!")
                .build();
    }
}

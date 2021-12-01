package com.telran.controller.auth;

import com.telran.dao.auth.UserRepository;
import com.telran.dao.contact.ContactRepository;
import com.telran.dto.AuthorizationDto;
import com.telran.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Username;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Account Controller")

@RestController
@RequestMapping(value = "auth")
@Validated
public class AuthController {
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    public AuthController(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    @ApiOperation(value = "Registration New User")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("registrations")
    public void registration(@RequestBody @Valid AuthorizationDto body){
        userRepository.addUser(body.getEmail(),body.getPassword());
    }

    @ApiOperation(value = "Delete User")
    @DeleteMapping("remove/{username}")
    public void deleteUser(@PathVariable @Username String username){
        userRepository.removeUser(username);
        contactRepository.removeAllContacts(username);
    }

    @ApiOperation(value = "Get All Users")
    @GetMapping("all")
    public List<UserDto> getAllUsers(){
        return userRepository.getAll()
                .map(e -> UserDto.builder()
                        .email(e.getEmail())
                        .roles(e.getRoles().stream().map(s -> s.replaceAll("ROLE_","")).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}

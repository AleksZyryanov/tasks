package com.telran.contacts.dao.auth;

import com.telran.contacts.exceptions.UserExistingException;
import com.telran.contacts.pojo.dto.UserDto;
import com.telran.contacts.pojo.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class UserRepositoryImpl implements UserRepository {

    final Map<String, UserEntity> map = new ConcurrentHashMap<>();
    final PasswordEncoder encoder;
    final ModelMapper mapper;

    public UserRepositoryImpl(PasswordEncoder encoder, ModelMapper mapper) {
        this.encoder = encoder;
        this.mapper = mapper;
    }

    @Value("${admin.login}")
    private String admLogin;
    @Value("${admin.password}")
    private String admPass;

    @PostConstruct
    public void init(){
        map.put("admin",UserEntity.builder().email(admLogin).password(encoder.encode(admPass)).roles(List.of("ROLE_ADMIN","ROLE_USER")).build());
    }

    @Override
    public void addUser(String email, String password) {
        UserEntity user = UserEntity.builder()
                .email(email)
                .password(encoder.encode(password))
                .roles(List.of("ROLE_USER"))
                .build();
        if(map.putIfAbsent(email, user) != null){
            throw new UserExistingException("User with email: %s already exist".formatted(email));
        }
    }

    @Override
    public UserDto getUser(String username) {
        return mapper.map(map.get(username),UserDto.class);
    }

    @Override
    public void removeUser(String username) {
        if (map.remove(username) == null)
            throw new UserExistingException("User: %s does not exists".formatted(username));
    }

    @Override
    public Stream<UserDto> getAll() {
        return map.values().stream().map(userEntity -> mapper.map(userEntity,UserDto.class));
    }

    @Override
    public UserEntity getUserByUserName(String username) {
        return map.get(username);
    }
}

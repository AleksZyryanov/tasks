package com.telran.contacts.service;

import com.telran.contacts.dao.auth.UserRepository;
import com.telran.contacts.pojo.dto.UserDto;
import com.telran.contacts.pojo.entity.UserEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.getUserByUserName(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new User(user.getEmail(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().toArray(String[]::new)));
    }
}

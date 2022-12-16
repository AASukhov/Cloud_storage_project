package com.example.diploma.service;

//import com.example.diploma.entity.Role;
import com.example.diploma.entity.User;
import com.example.diploma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();

        User user = userRepository.findUserByLogin(login).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(),authorities);
    }
}

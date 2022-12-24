package com.example.diploma.controller;

import com.example.diploma.dto.AuthResponseDto;
import com.example.diploma.dto.LoginDto;
import com.example.diploma.dto.UserDto;
import com.example.diploma.entity.User;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.security.JwtCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping()
public class AuthController {

    private AuthenticationManager manager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private JwtCreator creator;

    @Autowired
    public AuthController(AuthenticationManager manager,
                          UserRepository userRepository,
//                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtCreator creator) {
        this.manager = manager;
        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.creator = creator;

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login (@RequestBody LoginDto loginDto) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getLogin(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = creator.createToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping ("/register")
    public ResponseEntity<String> register (@RequestBody UserDto userDto) {
        if (userRepository.existsByLogin(userDto.getLogin())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setLogin(userDto.getLogin());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }
}

package com.example.diploma.service;

import com.example.diploma.dto.AuthResponseDto;
import com.example.diploma.dto.LoginDto;
import com.example.diploma.security.JwtCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private AuthenticationManager manager;
    private JwtCreator creator;

    public AuthService(AuthenticationManager manager,
                       JwtCreator creator) {
        this.manager = manager;
        this.creator = creator;
    }

    public ResponseEntity<AuthResponseDto> login (@RequestBody LoginDto loginDto) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = creator.createToken(authentication);
        AuthResponseDto responseDto = new AuthResponseDto(token);
        if (responseDto.getAccessToken() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            request.getSession().invalidate();
        }
        return new ResponseEntity<>("Successfully logged out", HttpStatus.PERMANENT_REDIRECT);
    }
}

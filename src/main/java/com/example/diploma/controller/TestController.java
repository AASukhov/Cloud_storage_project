package com.example.diploma.controller;

import com.example.diploma.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping
public class TestController {

    private final FileService service;

    @GetMapping("/user")
    public String isUserExisting(@RequestHeader("auth-token") String token, @RequestParam("login") String login) {
        return service.isUserExisting(login);
    }
}

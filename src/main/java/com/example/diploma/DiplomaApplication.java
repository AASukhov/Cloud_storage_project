package com.example.diploma;

import com.example.diploma.entity.User;
import com.example.diploma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DiplomaApplication{

	public static void main(String[] args) {
		SpringApplication.run(DiplomaApplication.class, args);

	}
}

package com.example.diploma;

import com.example.diploma.entity.User;
import com.example.diploma.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public ApplicationRunner(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        userRepository.save(User.builder()
                .login("admin@gmail.ru")
                .password(encoder.encode("0000"))
                .build());
        userRepository.save(User.builder()
                .login("reader@gmail.ru")
                .password(encoder.encode("1111"))
                .build());
        userRepository.save(User.builder()
                .login("editor@gmail.ru")
                .password(encoder.encode("2222"))
                .build());
    }
}

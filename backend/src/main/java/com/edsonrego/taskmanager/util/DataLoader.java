package com.edsonrego.taskmanager.util;

import com.edsonrego.taskmanager.model.User;
import com.edsonrego.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin")); // senha codificada
                admin.setRole("ROLE_USER");
                userRepository.save(admin);
                System.out.println("Admin user created with username 'admin' and password 'admin'");
            }
        };
    }
}

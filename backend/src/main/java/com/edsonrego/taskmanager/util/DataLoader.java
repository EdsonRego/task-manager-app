package com.edsonrego.taskmanager.util;

import com.edsonrego.taskmanager.model.User;
import com.edsonrego.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Criar admin se não existir
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setName("admin");  // <- Alterado de setUsername
            admin.setEmail("admin@example.com");
            admin.setPassword("admin123"); // Senha simples para teste
            userRepository.save(admin);
        }
    }
}

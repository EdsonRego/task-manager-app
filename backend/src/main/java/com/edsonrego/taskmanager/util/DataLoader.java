package com.edsonrego.taskmanager.util;

import com.edsonrego.taskmanager.model.Task;
import com.edsonrego.taskmanager.model.User;
import com.edsonrego.taskmanager.repository.TaskRepository;
import com.edsonrego.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsersAndTasks(UserRepository userRepository,
                                        PasswordEncoder passwordEncoder,
                                        TaskRepository taskRepository) {
        return args -> {
            // Criar usuário admin, se não existir
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin")); // senha codificada
                admin.setRole("ROLE_USER");
                userRepository.save(admin);
                System.out.println("Admin user created with username 'admin' and password 'admin'");
            }

            // Criar algumas tasks de exemplo, se não existirem
            if (taskRepository.count() == 0) {
                Task t1 = new Task();
                t1.setTitle("Finish README");
                t1.setDescription("Update README for the portfolio");
                t1.setDueDate(LocalDate.now().plusDays(3));
                t1.setCompleted(false);
                taskRepository.save(t1);

                Task t2 = new Task();
                t2.setTitle("Implement TaskController");
                t2.setDescription("Create controllers and endpoints for tasks");
                t2.setDueDate(LocalDate.now().plusDays(7));
                t2.setCompleted(false);
                taskRepository.save(t2);

                System.out.println("Sample tasks created in database.");
            }
        };
    }
}

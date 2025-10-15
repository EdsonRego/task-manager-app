package com.edsonrego.taskmanager.controller;

import com.edsonrego.taskmanager.model.User;
import com.edsonrego.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User adminUser;
    private User normalUser;

    @BeforeEach
    void setup() {
        // Limpa a tabela e garante execução imediata
        userRepository.deleteAll();
        userRepository.flush();

        // Cria admin
        adminUser = new User();
        adminUser.setName("admin");
        adminUser.setPassword("password");
        adminUser.setEmail("admin@example.com");
        adminUser.setRole("ADMIN");
        userRepository.saveAndFlush(adminUser);

        // Cria usuário normal
        normalUser = new User();
        normalUser.setName("john");
        normalUser.setPassword("1234");
        normalUser.setEmail("john@example.com");
        normalUser.setRole("USER");
        userRepository.saveAndFlush(normalUser);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldCreateUser() throws Exception {
        // Garante que email seja único
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@example.com";

        String json = String.format("""
        {
            "name": "newuser",
            "password": "pass123",
            "email": "%s",
            "role": "USER"
        }
    """, uniqueEmail);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated()) // 201 Created
                .andExpect(jsonPath("$.name").value("newuser"))
                .andExpect(jsonPath("$.email").value(uniqueEmail))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // admin + john
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnUserById() throws Exception {
        Long id = adminUser.getId();

        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", notNullValue()))
                .andExpect(jsonPath("$.email", notNullValue()));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldUpdateUser() throws Exception {
        Long id = adminUser.getId();

        String json = """
            {
                "name": "adminUpdated",
                "password": "newpass",
                "email": "updated@example.com",
                "role": "ADMIN"
            }
        """;

        mockMvc.perform(put("/api/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("adminUpdated"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldDeleteUser() throws Exception {
        Long id = normalUser.getId();

        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isNotFound());
    }
}

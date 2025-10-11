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
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("password");
        user1.setRole("ADMIN");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("john");
        user2.setPassword("1234");
        user2.setRole("USER");
        userRepository.save(user2);
    }

    // ✅ Create
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldCreateUser() throws Exception {
        String json = """
            {
                "username": "newuser",
                "password": "pass123",
                "role": "USER"
            }
        """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"));
    }

    // ✅ Get all
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // ✅ Search users
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldSearchUsersByUsername() throws Exception {
        mockMvc.perform(get("/api/users/search").param("username", "john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("john"));
    }

    // ✅ Get authenticated user
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"));
    }

    // ✅ Update user
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldUpdateUser() throws Exception {
        Long id = userRepository.findAll().get(0).getId();

        String json = """
            {
                "username": "adminUpdated",
                "password": "newpass",
                "role": "ADMIN"
            }
        """;

        mockMvc.perform(put("/api/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("adminUpdated"));
    }

    // ✅ Delete user
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldDeleteUser() throws Exception {
        Long id = userRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isNoContent());
    }
}

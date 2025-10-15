package com.edsonrego.taskmanager.controller;

import com.edsonrego.taskmanager.model.Task;
import com.edsonrego.taskmanager.model.User;
import com.edsonrego.taskmanager.repository.TaskRepository;
import com.edsonrego.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setup() {
        taskRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new User();
        testUser.setName("johndoe");
        testUser.setPassword("1234");
        testUser.setEmail("johndoe@example.com");
        testUser.setRole("USER");
        userRepository.save(testUser);

        Task task1 = new Task();
        task1.setTitle("Buy groceries");
        task1.setDescription("Milk, eggs, bread");
        task1.setDueDate(LocalDate.now().plusDays(1));
        task1.setUser(testUser);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Study Spring Boot");
        task2.setDescription("Review integration testing");
        task2.setDueDate(LocalDate.now().plusDays(2));
        task2.setUser(testUser);
        taskRepository.save(task2);
    }

    @Test
    @WithMockUser(username = "johndoe", roles = "USER")
    void shouldReturnAllTasks() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", notNullValue()))
                .andExpect(jsonPath("$[1].title", notNullValue()));
    }

    @Test
    @WithMockUser(username = "johndoe", roles = "USER")
    void shouldCreateTask() throws Exception {
        String json = String.format("""
            {
                "title": "Complete homework",
                "description": "Finish math exercises",
                "dueDate": "%s",
                "userId": %d
            }
        """, LocalDate.now().plusDays(3), testUser.getId());

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated()) // 201 Created
                .andExpect(jsonPath("$.title").value("Complete homework"))
                .andExpect(jsonPath("$.description").value("Finish math exercises"));
    }

    @Test
    @WithMockUser(username = "johndoe", roles = "USER")
    void shouldReturnTaskById() throws Exception {
        Long id = taskRepository.findAll().get(0).getId();

        mockMvc.perform(get("/api/tasks/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", notNullValue()))
                .andExpect(jsonPath("$.description", notNullValue()));
    }

    @Test
    @WithMockUser(username = "johndoe", roles = "USER")
    void shouldUpdateTask() throws Exception {
        Task existingTask = taskRepository.findAll().get(0);

        String json = String.format("""
            {
                "title": "Updated task",
                "description": "Updated description",
                "dueDate": "%s",
                "userId": %d
            }
        """, LocalDate.now().plusDays(5), testUser.getId());

        mockMvc.perform(put("/api/tasks/" + existingTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated task"))
                .andExpect(jsonPath("$.description").value("Updated description"));
    }

    @Test
    @WithMockUser(username = "johndoe", roles = "USER")
    void shouldDeleteTask() throws Exception {
        Task existingTask = taskRepository.findAll().get(0);

        mockMvc.perform(delete("/api/tasks/" + existingTask.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/tasks/" + existingTask.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "johndoe", roles = "USER")
    void shouldReturnTasksDueSoon() throws Exception {
        List<Task> tasks = taskRepository.findAll();
        mockMvc.perform(get("/api/tasks/due-soon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(tasks.size())));
    }
}

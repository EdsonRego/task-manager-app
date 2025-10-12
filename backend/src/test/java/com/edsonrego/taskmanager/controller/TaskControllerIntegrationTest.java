package com.edsonrego.taskmanager.controller;

import com.edsonrego.taskmanager.model.Task;
import com.edsonrego.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setup() {
        taskRepository.deleteAll();

        Task task1 = new Task();
        task1.setTitle("Finish project");
        task1.setDescription("Complete the backend module");
        task1.setCompleted(false);
        task1.setDueDate(LocalDate.now().plusDays(2));
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Pay bills");
        task2.setDescription("Electricity and water");
        task2.setCompleted(true);
        task2.setDueDate(LocalDate.now().minusDays(1));
        taskRepository.save(task2);
    }

    // ✅ Create task
    @Test
    @WithMockUser(username = "admin", roles = "USER")
    void shouldCreateTask() throws Exception {
        String taskJson = """
            {
                "title": "New Task",
                "description": "Testing creation",
                "completed": false
            }
        """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated()) // Ajustado para 201
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    // ✅ Get all tasks
    @Test
    @WithMockUser(username = "admin", roles = "USER")
    void shouldReturnAllTasks() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // ✅ Get completed tasks
    @Test
    @WithMockUser(username = "admin", roles = "USER")
    void shouldReturnCompletedTasks() throws Exception {
        mockMvc.perform(get("/api/tasks/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(true));
    }

    // ✅ Get pending tasks
    @Test
    @WithMockUser(username = "admin", roles = "USER")
    void shouldReturnPendingTasks() throws Exception {
        mockMvc.perform(get("/api/tasks/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    // ✅ Get overdue tasks
    @Test
    @WithMockUser(username = "admin", roles = "USER")
    void shouldReturnOverdueTasks() throws Exception {
        mockMvc.perform(get("/api/tasks/overdue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Pay bills"));
    }

    // ✅ Search by keyword
    @Test
    @WithMockUser(username = "admin", roles = "USER")
    void shouldSearchTasksByKeyword() throws Exception {
        mockMvc.perform(get("/api/tasks/search").param("keyword", "Finish"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Finish project"));
    }

    // ✅ Tasks due soon (next 3 days)
    @Test
    @WithMockUser(username = "admin", roles = "USER")
    void shouldReturnTasksDueSoon() throws Exception {
        mockMvc.perform(get("/api/tasks/due-soon").param("days", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Finish project"));
    }

    // ✅ Delete task
    @Test
    @WithMockUser(username = "admin", roles = "USER")
    void shouldDeleteTask() throws Exception {
        Long idToDelete = taskRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/api/tasks/{id}", idToDelete))
                .andExpect(status().isNoContent());

        // Garantir que foi removida
        mockMvc.perform(get("/api/tasks/{id}", idToDelete))
                .andExpect(status().isNotFound());
    }
}

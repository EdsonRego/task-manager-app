package com.edsonrego.taskmanager.controller;

import com.edsonrego.taskmanager.model.Task;
import com.edsonrego.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return ResponseEntity.ok(taskService.updateTask(id, updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ 1. Listar tarefas concluídas
    @GetMapping("/completed")
    public List<Task> getCompletedTasks() {
        return taskService.getAllTasks()
                .stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    // ✅ 2. Listar tarefas pendentes
    @GetMapping("/pending")
    public List<Task> getPendingTasks() {
        return taskService.getAllTasks()
                .stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

    // ✅ 3. Listar tarefas vencidas
    @GetMapping("/overdue")
    public List<Task> getOverdueTasks() {
        LocalDate today = LocalDate.now();
        return taskService.getAllTasks()
                .stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    // ✅ 4. Buscar por palavra-chave no título
    @GetMapping("/search")
    public List<Task> searchTasks(@RequestParam("keyword") String keyword) {
        return taskService.getAllTasks()
                .stream()
                .filter(task -> task.getTitle() != null && task.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // ✅ 5. Listar tarefas com vencimento próximo
    @GetMapping("/due-soon")
    public List<Task> getTasksDueSoon(@RequestParam(defaultValue = "3") int days) {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(days);
        return taskService.getAllTasks()
                .stream()
                .filter(task -> task.getDueDate() != null &&
                        !task.getDueDate().isBefore(today) &&
                        task.getDueDate().isBefore(limit))
                .collect(Collectors.toList());
    }
}

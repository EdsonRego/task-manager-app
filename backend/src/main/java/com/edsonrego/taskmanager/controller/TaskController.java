package com.edsonrego.taskmanager.controller;

import com.edsonrego.taskmanager.dto.TaskDTO;
import com.edsonrego.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO created = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        TaskDTO updated = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TaskDTO>> getCompletedTasks() {
        return ResponseEntity.ok(taskService.getCompletedTasks());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TaskDTO>> getPendingTasks() {
        return ResponseEntity.ok(taskService.getPendingTasks());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<TaskDTO>> getOverdueTasks() {
        return ResponseEntity.ok(taskService.getOverdueTasks());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskDTO>> searchTasks(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(taskService.searchTasks(keyword));
    }

    @GetMapping("/due-soon")
    public ResponseEntity<List<TaskDTO>> getTasksDueSoon(@RequestParam(defaultValue = "3") int days) {
        return ResponseEntity.ok(taskService.getTasksDueSoon(days));
    }
}

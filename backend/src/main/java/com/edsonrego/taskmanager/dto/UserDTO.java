package com.edsonrego.taskmanager.dto;

import java.util.List;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private List<TaskDTO> tasks;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, String role, List<TaskDTO> tasks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.tasks = tasks;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<TaskDTO> getTasks() { return tasks; }
    public void setTasks(List<TaskDTO> tasks) { this.tasks = tasks; }
}

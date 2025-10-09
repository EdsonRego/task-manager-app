package com.edsonrego.taskmanager.service;

import com.edsonrego.taskmanager.model.Task;
import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
}

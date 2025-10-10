package com.edsonrego.taskmanager.service;

import com.edsonrego.taskmanager.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);
}

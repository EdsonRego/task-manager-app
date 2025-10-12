package com.edsonrego.taskmanager.service;

import com.edsonrego.taskmanager.model.Task;
import java.util.List;

public interface TaskService {

    Task createTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);

    List<Task> getCompletedTasks();

    List<Task> getPendingTasks();

    List<Task> getOverdueTasks();

    List<Task> searchTasks(String keyword);

    List<Task> getTasksDueSoon(int days);
}

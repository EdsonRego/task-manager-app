package com.edsonrego.taskmanager.service;

import com.edsonrego.taskmanager.dto.TaskDTO;
import java.util.List;

public interface TaskService {

    List<TaskDTO> getAllTasks();
    TaskDTO getTaskById(Long id);
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
    void deleteTask(Long id);

    List<TaskDTO> getCompletedTasks();
    List<TaskDTO> getPendingTasks();
    List<TaskDTO> getOverdueTasks();
    List<TaskDTO> searchTasks(String keyword);
    List<TaskDTO> getTasksDueSoon(int days);
}

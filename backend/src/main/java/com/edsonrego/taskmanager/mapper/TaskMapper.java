package com.edsonrego.taskmanager.mapper;

import com.edsonrego.taskmanager.dto.TaskDTO;
import com.edsonrego.taskmanager.model.Task;
import com.edsonrego.taskmanager.model.User;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        if (task == null) return null;
        Long userId = null;
        if (task.getUser() != null) {
            userId = task.getUser().getId();
        }
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.isCompleted(),
                userId
        );
    }

    // ✅ Sobrecarga para não quebrar código existente
    public static Task toEntity(TaskDTO dto) {
        return toEntity(dto, null);
    }

    // ✅ Método principal (caso precise associar um usuário)
    public static Task toEntity(TaskDTO dto, User user) {
        if (dto == null) return null;
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setCompleted(dto.isCompleted());
        task.setUser(user);
        return task;
    }
}

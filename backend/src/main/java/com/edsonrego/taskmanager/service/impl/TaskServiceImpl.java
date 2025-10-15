package com.edsonrego.taskmanager.service.impl;

import com.edsonrego.taskmanager.dto.TaskDTO;
import com.edsonrego.taskmanager.exception.ResourceNotFoundException;
import com.edsonrego.taskmanager.mapper.TaskMapper;
import com.edsonrego.taskmanager.model.Task;
import com.edsonrego.taskmanager.model.User;
import com.edsonrego.taskmanager.repository.TaskRepository;
import com.edsonrego.taskmanager.repository.UserRepository;
import com.edsonrego.taskmanager.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // Construtor para injeção
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        return TaskMapper.toDTO(task);
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        User user = null;
        if (taskDTO.getUserId() != null) {
            user = userRepository.findById(taskDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + taskDTO.getUserId()));
        }

        Task task = TaskMapper.toEntity(taskDTO, user);
        Task saved = taskRepository.save(task);
        return TaskMapper.toDTO(saved);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        // Atualiza campos permitidos
        if (taskDTO.getTitle() != null) existing.setTitle(taskDTO.getTitle());
        if (taskDTO.getDescription() != null) existing.setDescription(taskDTO.getDescription());
        if (taskDTO.getDueDate() != null) existing.setDueDate(taskDTO.getDueDate());
        existing.setCompleted(taskDTO.isCompleted());

        // Atualiza associação com usuário (se informado)
        if (taskDTO.getUserId() != null) {
            User user = userRepository.findById(taskDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + taskDTO.getUserId()));
            existing.setUser(user);
        } else {
            // se quiser que um null no userId desassocie, trate aqui (atualmente nada faz)
        }

        Task updated = taskRepository.save(existing);
        return TaskMapper.toDTO(updated);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> getCompletedTasks() {
        return taskRepository.findByCompleted(true)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getPendingTasks() {
        return taskRepository.findByCompleted(false)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getOverdueTasks() {
        LocalDate today = LocalDate.now();
        return taskRepository.findByDueDateBefore(today)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> searchTasks(String keyword) {
        // delega para query customizada no repositório
        return taskRepository.searchByKeyword(keyword)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksDueSoon(int days) {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(days);
        return taskRepository.findByDueDateBetween(today, limit)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }
}

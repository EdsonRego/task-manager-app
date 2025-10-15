package com.edsonrego.taskmanager.service;

import com.edsonrego.taskmanager.dto.TaskDTO;
import com.edsonrego.taskmanager.exception.ResourceNotFoundException;
import com.edsonrego.taskmanager.mapper.TaskMapper;
import com.edsonrego.taskmanager.model.Task;
import com.edsonrego.taskmanager.repository.TaskRepository;
import com.edsonrego.taskmanager.service.impl.TaskServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ CREATE
    @Test
    void testCreateTask() {
        TaskDTO dto = new TaskDTO();
        dto.setTitle("New Task");
        dto.setDescription("Description");

        Task entity = TaskMapper.toEntity(dto);
        entity.setId(1L);

        when(taskRepository.save(any(Task.class))).thenReturn(entity);

        TaskDTO result = taskService.createTask(dto);

        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // ✅ GET BY ID (SUCCESS)
    @Test
    void testGetTaskById_Success() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository).findById(1L);
    }

    // ✅ GET BY ID (NOT FOUND)
    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(99L));
        verify(taskRepository).findById(99L);
    }

    // ✅ GET ALL
    @Test
    void testGetAllTasks() {
        Task task = new Task();
        task.setTitle("Task 1");

        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskDTO> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        verify(taskRepository).findAll();
    }

    // ✅ UPDATE
    @Test
    void testUpdateTask_Success() {
        Task existing = new Task();
        existing.setId(1L);
        existing.setTitle("Old Title");

        TaskDTO updatedDTO = new TaskDTO();
        updatedDTO.setTitle("Updated Title");
        updatedDTO.setDescription("Updated Desc");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskDTO result = taskService.updateTask(1L, updatedDTO);

        assertEquals("Updated Title", result.getTitle());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.updateTask(1L, new TaskDTO()));
        verify(taskRepository).findById(1L);
    }

    // ✅ DELETE
    @Test
    void testDeleteTask_Success() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository).existsById(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> taskService.deleteTask(99L));
        verify(taskRepository).existsById(99L);
    }

    // ✅ COMPLETED TASKS
    @Test
    void testGetCompletedTasks() {
        Task task = new Task();
        task.setCompleted(true);
        task.setTitle("Done Task");

        when(taskRepository.findByCompleted(true)).thenReturn(List.of(task));

        List<TaskDTO> result = taskService.getCompletedTasks();

        assertEquals(1, result.size());
        assertTrue(result.get(0).isCompleted());
        verify(taskRepository).findByCompleted(true);
    }

    // ✅ PENDING TASKS
    @Test
    void testGetPendingTasks() {
        Task task = new Task();
        task.setCompleted(false);
        task.setTitle("Pending Task");

        when(taskRepository.findByCompleted(false)).thenReturn(List.of(task));

        List<TaskDTO> result = taskService.getPendingTasks();

        assertEquals(1, result.size());
        assertFalse(result.get(0).isCompleted());
        verify(taskRepository).findByCompleted(false);
    }

    // ✅ OVERDUE TASKS
    @Test
    void testGetOverdueTasks() {
        Task task = new Task();
        task.setDueDate(LocalDate.now().minusDays(1));
        task.setTitle("Overdue Task");

        when(taskRepository.findByDueDateBefore(any(LocalDate.class))).thenReturn(List.of(task));

        List<TaskDTO> result = taskService.getOverdueTasks();

        assertEquals(1, result.size());
        verify(taskRepository).findByDueDateBefore(any(LocalDate.class));
    }

    // ✅ SEARCH TASKS
    @Test
    void testSearchTasks() {
        Task task = new Task();
        task.setTitle("Keyword match");

        when(taskRepository.searchByKeyword("keyword")).thenReturn(List.of(task));

        List<TaskDTO> result = taskService.searchTasks("keyword");

        assertEquals(1, result.size());
        assertEquals("Keyword match", result.get(0).getTitle());
        verify(taskRepository).searchByKeyword("keyword");
    }

    // ✅ TASKS DUE SOON
    @Test
    void testGetTasksDueSoon() {
        Task task = new Task();
        task.setTitle("Soon Task");
        task.setDueDate(LocalDate.now().plusDays(2));

        when(taskRepository.findByDueDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(task));

        List<TaskDTO> result = taskService.getTasksDueSoon(3);

        assertEquals(1, result.size());
        assertEquals("Soon Task", result.get(0).getTitle());
        verify(taskRepository).findByDueDateBetween(any(LocalDate.class), any(LocalDate.class));
    }
}

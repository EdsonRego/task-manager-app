package com.edsonrego.taskmanager.service.impl;

import com.edsonrego.taskmanager.dto.UserCreateDTO;
import com.edsonrego.taskmanager.dto.UserDTO;
import com.edsonrego.taskmanager.mapper.UserMapper;
import com.edsonrego.taskmanager.model.User;
import com.edsonrego.taskmanager.repository.UserRepository;
import com.edsonrego.taskmanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        // Arrange
        UserCreateDTO createDTO = new UserCreateDTO();
        createDTO.setName("Edson");
        createDTO.setEmail("edson@example.com");
        createDTO.setPassword("secret123");
        createDTO.setRole("USER");

        User entity = new User();
        entity.setId(1L);
        entity.setName("Edson");
        entity.setEmail("edson@example.com");
        entity.setPassword("secret123");
        entity.setRole("USER");

        when(userRepository.existsByEmail("edson@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(entity);

        // Act
        UserDTO result = userService.createUser(createDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Edson", result.getName());
        assertEquals("edson@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserShouldThrowWhenEmailAlreadyExists() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setName("Edson");
        dto.setEmail("edson@example.com");
        dto.setPassword("secret123");
        dto.setRole("USER");

        when(userRepository.existsByEmail("edson@example.com")).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testGetUserById() {
        User entity = new User();
        entity.setId(1L);
        entity.setName("Edson");
        entity.setEmail("edson@example.com");
        entity.setPassword("secret123");
        entity.setRole("USER");

        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Edson", result.getName());
        assertEquals("edson@example.com", result.getEmail());
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(99L));
        verify(userRepository, never()).deleteById(any());
    }
}

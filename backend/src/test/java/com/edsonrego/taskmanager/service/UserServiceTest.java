package com.edsonrego.taskmanager.service;

import com.edsonrego.taskmanager.dto.UserCreateDTO;
import com.edsonrego.taskmanager.dto.UserDTO;
import com.edsonrego.taskmanager.exception.ResourceNotFoundException;
import com.edsonrego.taskmanager.mapper.UserMapper;
import com.edsonrego.taskmanager.model.User;
import com.edsonrego.taskmanager.repository.UserRepository;
import com.edsonrego.taskmanager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para UserServiceImpl (camada de serviço).
 * - Verifique se os imports referenciam os pacotes corretos do seu projeto.
 */
class UserServiceTest {

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
        // Arrange: criar DTO de criação (inclui password)
        UserCreateDTO dto = new UserCreateDTO();
        dto.setName("Edson");
        dto.setEmail("edson@example.com");
        dto.setPassword("1234");
        dto.setRole("ROLE_USER");

        // Entidade que o repositório deve retornar
        User entity = new User();
        entity.setId(1L);
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());

        // Mock: garantir que não existe e-mail e simular save
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(entity);

        // Act
        UserDTO result = userService.createUser(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Edson", result.getName());
        assertEquals("edson@example.com", result.getEmail());
        assertEquals("ROLE_USER", result.getRole());

        verify(userRepository, times(1)).existsByEmail(dto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById_Success() {
        User user = new User();
        user.setId(1L);
        user.setName("Edson");
        user.setEmail("edson@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Edson", result.getName());
        assertEquals("edson@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99L));
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void testUpdateUser() {
        User existing = new User();
        existing.setId(1L);
        existing.setName("Old Name");
        existing.setEmail("old@example.com");

        UserDTO updateDTO = new UserDTO();
        updateDTO.setId(1L);
        updateDTO.setName("New Name");
        updateDTO.setEmail("new@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenReturn(existing);

        UserDTO result = userService.updateUser(1L, updateDTO);

        assertEquals("New Name", result.getName());
        assertEquals("new@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(Exception.class, () -> userService.deleteUser(1L));
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User("Edson", "edson@example.com", "1234", "ROLE_USER");
        user1.setId(1L);

        User user2 = new User("Maria", "maria@example.com", "abcd", "ROLE_USER");
        user2.setId(2L);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Edson", result.get(0).getName());
        assertEquals("Maria", result.get(1).getName());
        verify(userRepository, times(1)).findAll();
    }
}

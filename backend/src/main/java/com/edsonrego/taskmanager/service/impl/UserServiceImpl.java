package com.edsonrego.taskmanager.service.impl;

import com.edsonrego.taskmanager.dto.UserDTO;
import com.edsonrego.taskmanager.dto.UserCreateDTO;
import com.edsonrego.taskmanager.exception.ResourceNotFoundException;
import com.edsonrego.taskmanager.mapper.UserMapper;
import com.edsonrego.taskmanager.model.User;
import com.edsonrego.taskmanager.repository.UserRepository;
import com.edsonrego.taskmanager.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserCreateDTO dto) {
        // usa existsByEmail para checagem rápida
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado: " + dto.getEmail());
        }

        User entity = UserMapper.toEntity(dto); // usa mapper que preenche password
        User saved = userRepository.save(entity);

        return UserMapper.toDTO(saved);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));

        existingUser.setName(updatedUserDTO.getName());
        existingUser.setEmail(updatedUserDTO.getEmail());
        if (updatedUserDTO.getRole() != null) existingUser.setRole(updatedUserDTO.getRole());

        User updated = userRepository.save(existingUser);
        return UserMapper.toDTO(updated);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado: " + id);
        }
        userRepository.deleteById(id);
    }
}

package com.edsonrego.taskmanager.service;

import com.edsonrego.taskmanager.dto.UserDTO;
import com.edsonrego.taskmanager.dto.UserCreateDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserCreateDTO userCreateDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO updatedUserDTO);
    void deleteUser(Long id);
}

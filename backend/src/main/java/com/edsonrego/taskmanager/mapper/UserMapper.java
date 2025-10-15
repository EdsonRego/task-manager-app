package com.edsonrego.taskmanager.mapper;

import com.edsonrego.taskmanager.dto.UserCreateDTO;
import com.edsonrego.taskmanager.dto.UserDTO;
import com.edsonrego.taskmanager.model.User;

import java.util.Collections;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getTasks() != null
                        ? user.getTasks().stream().map(TaskMapper::toDTO).collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }

    // toEntity a partir de UserDTO (não inclui password — ok para updates)
    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole() == null ? "USER" : dto.getRole());
        return user;
    }

    // toEntity a partir de UserCreateDTO: inclui password
    public static User toEntity(UserCreateDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole() == null ? "USER" : dto.getRole());
        return user;
    }

    public static void updateEntity(User user, UserDTO dto) {
        if (user == null || dto == null) return;
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getRole() != null) user.setRole(dto.getRole());
    }
}

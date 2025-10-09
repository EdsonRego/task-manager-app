package com.edsonrego.taskmanager.service;

import com.edsonrego.taskmanager.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> findByUsername(String username);
    List<User> getAllUsers();
}

package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Service class for managing {@link User} entities.
 * Provides business logic for CRUD operations on users.
 */
@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * Retrieves all users from the database.
     *
     * @return a list of all {@link User} entities
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link User} entity
     * @throws IllegalArgumentException if no user is found with the given ID
     */
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
    }
    /**
     * Creates a new user and saves it to the database.
     *
     * @param user the {@link User} entity to create
     */
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }
    /**
     * Updates an existing user with new values.
     *
     * @param id          the ID of the user to update
     * @param updatedUser the new values for the user
     * @return the updated {@link User} entity
     * @throws IllegalArgumentException if no user is found with the given ID
     */
    public User update(Integer id, User updatedUser) {
        User existingUser = findById(id);
        existingUser.setFullname(updatedUser.getFullname());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setRole(updatedUser.getRole());


        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return userRepository.save(updatedUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws IllegalArgumentException if no user is found with the given ID
     */
    public void delete(Integer id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}


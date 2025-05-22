package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
    }

    public void create(User user) {
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    public User update(Integer id, User updatedUser) {
        User existingUser = findById(id);
        updatedUser.setId(id);
        updatedUser.setPassword(updatedUser.getPassword());
        return userRepository.save(updatedUser);
    }

    public void delete(Integer id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}


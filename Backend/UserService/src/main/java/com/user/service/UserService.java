package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.user.bean.User;
import com.user.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user, String passwordStrength) {
        // Check if a user with the provided username already exists
        User existingUser = repo.findByUsername(user.getUsername());
        if (existingUser != null) {
            return null;
        }
        // If the username is available, proceed with user registration
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordStrength(passwordStrength);
        return repo.save(user);
    }
    
    public User findUserByUsername(String username) {
        return repo.findByUsername(username);
    }
}

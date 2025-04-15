package com.iphone_store.iphone_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iphone_store.iphone_store.entity.User;
import com.iphone_store.iphone_store.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName()).orElseThrow();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}

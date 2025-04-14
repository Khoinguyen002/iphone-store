package com.iphone_store.iphone_store.service;
import com.iphone_store.iphone_store.entity.User;

public interface UserService {
    void save(User user);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}

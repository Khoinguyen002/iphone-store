package com.iphone_store.iphone_store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iphone_store.iphone_store.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

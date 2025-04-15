package com.iphone_store.iphone_store.repository;

import com.iphone_store.iphone_store.entity.Cart;
import com.iphone_store.iphone_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
package com.iphone_store.iphone_store.service;

import com.iphone_store.iphone_store.entity.*;
import com.iphone_store.iphone_store.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService UserService;

    public Cart getCurrentUserCart() {
        User user = UserService.getCurrentUser();
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = Cart.builder().user(user).build();
            return cartRepository.save(cart);
        });
    }

    public int getTotal() {
        return getCurrentUserCart().getItems().size();
    }

    public void addToCart(Long productId) {
        Cart cart = getCurrentUserCart();

        Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.addItem(product, 1);
        cartRepository.save(cart);
    }

    public void updateCartItem(Long productId, int quantity) {
        Cart cart = getCurrentUserCart();
        cart.updateQuantity(productId, quantity);
        cartRepository.save(cart);
    }

    public void removeFromCart(Long productId) {
        Cart cart = getCurrentUserCart();
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

 
}
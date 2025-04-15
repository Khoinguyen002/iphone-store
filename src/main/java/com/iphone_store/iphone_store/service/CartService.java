package com.iphone_store.iphone_store.service;

import com.iphone_store.iphone_store.entity.*;
import com.iphone_store.iphone_store.repository.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService UserService;

    public Cart getCurrentUserCart() {
        User user = UserService.getCurrentUser();
        return cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart cart = Cart.builder().user(user).build();
            return cartRepository.save(cart);
        });
    }

    public BigDecimal getOriginalTotalPrice() {
        return getCurrentUserCart().getItems().stream()
                .map(item -> BigDecimal.valueOf(item.getProduct().getPrice() * item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPrice() {
        return getCurrentUserCart().getItems().stream()
                .map(item -> {
                    Product product = item.getProduct();
                    long discountedPrice = product.getPrice() * (100 - product.getDiscount()) / 100;
                    return BigDecimal.valueOf(discountedPrice * item.getQuantity());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getSavingPrice() {
        return getCurrentUserCart().getItems().stream()
                .map(item -> {
                    Product p = item.getProduct();
                    long savingPerItem = p.getPrice() * p.getDiscount() / 100;
                    return BigDecimal.valueOf(savingPerItem * item.getQuantity());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
        if (quantity == 0) {
            cart.removeItem(productId);
            return;
        }
        cart.updateQuantity(productId, quantity);
        cartRepository.save(cart);
    }

    public void removeFromCart(Long productId) {
        Cart cart = getCurrentUserCart();
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

}
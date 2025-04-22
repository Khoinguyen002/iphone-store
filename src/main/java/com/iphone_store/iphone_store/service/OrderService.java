package com.iphone_store.iphone_store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iphone_store.iphone_store.entity.Cart;
import com.iphone_store.iphone_store.entity.Order;
import com.iphone_store.iphone_store.entity.OrderItem;
import com.iphone_store.iphone_store.entity.OrderStatus;
import com.iphone_store.iphone_store.entity.Product;
import com.iphone_store.iphone_store.entity.User;
import com.iphone_store.iphone_store.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(Cart cart, User user) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        cart.getItems().forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        });

        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setOrderStatus(OrderStatus.PENDING_CONFIRM);

        orderRepository.save(order);
    }

     public BigDecimal getOriginalTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> BigDecimal.valueOf(item.getProduct().getPrice() * item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> {
                    Product product = item.getProduct();
                    long discountedPrice = product.getPrice() * (100 - product.getDiscount()) / 100;
                    return BigDecimal.valueOf(discountedPrice * item.getQuantity());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getSavingPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> {
                    Product p = item.getProduct();
                    long savingPerItem = p.getPrice() * p.getDiscount() / 100;
                    return BigDecimal.valueOf(savingPerItem * item.getQuantity());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void confirmOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(null);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
    }

    public void rejectOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(null);
        order.setOrderStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }

    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(null);
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public List<Order> getAllByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(null);
    }
}

package com.iphone_store.iphone_store.controller;

import com.iphone_store.iphone_store.entity.Cart;
import com.iphone_store.iphone_store.entity.Order;
import com.iphone_store.iphone_store.entity.User;
import com.iphone_store.iphone_store.service.CartService;
import com.iphone_store.iphone_store.service.OrderService;
import com.iphone_store.iphone_store.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView list() {
        User user = userService.getCurrentUser();
        ModelAndView mav = new ModelAndView("/orders/list");
        mav.addObject("orders", orderService.getAllByUserId(user.getId()));
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/orders/details");
        Order order = orderService.getOrderById(id);
        mav.addObject("order", order);
        mav.addObject("totalPrice", orderService.getTotalPrice(order.getOrderItems()));
        mav.addObject("originalTotalPrice", orderService.getOriginalTotalPrice(order.getOrderItems()));
        mav.addObject("savingPrice", orderService.getSavingPrice(order.getOrderItems()));
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createOrder() {
        User user = userService.getCurrentUser(); 
        Cart cart = cartService.getCurrentUserCart();

        orderService.createOrder(cart, user);
        cartService.removeCart(cart.getId());
        return new ModelAndView("redirect:/orders");
    }

    @PostMapping("/cancel/{id}")
    public ModelAndView cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return new ModelAndView("redirect:/orders");
    }
}

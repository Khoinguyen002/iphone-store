package com.iphone_store.iphone_store.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iphone_store.iphone_store.service.CartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ModelAndView showCart() {
        ModelAndView mav = new ModelAndView("cart");
        mav.addObject("cartItems", cartService.getCurrentUserCart().getItems());
        mav.addObject("totalPrice", cartService.getTotalPrice());
        mav.addObject("originalTotalPrice", cartService.getOriginalTotalPrice());
        mav.addObject("savingPrice", cartService.getSavingPrice());
        return mav;
    }

    @PostMapping("/add/{productId}")
    public ModelAndView addToCart(@PathVariable Long productId) {
        cartService.addToCart(productId);
        return new ModelAndView("redirect:/cart");
    }

    @PostMapping("/update/{productId}")
    public ModelAndView updateCart(
            @PathVariable Long productId,
            @RequestParam int quantity,
            @RequestParam String action) {
        if ("increase".equals(action)) {
            quantity++;
        } else if ("decrease".equals(action)) {
            quantity = Math.max(1, quantity - 1); // không giảm < 1
        }
        cartService.updateCartItem(productId, quantity);
        return new ModelAndView("redirect:/cart");
    }

    @PostMapping("/remove/{productId}")
    public ModelAndView removeFromCart(@PathVariable Long productId, Principal principal) {
        cartService.removeFromCart(productId);
        return new ModelAndView("redirect:/cart");
    }
}

package com.iphone_store.iphone_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.iphone_store.iphone_store.service.OrderService;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/orders/adminList");
        mav.addObject("orders", orderService.getAll());
        return mav;
    }

    @PostMapping("/confirm/{id}")
    public ModelAndView confirmOrder(@PathVariable Long id) {
        orderService.confirmOrder(id);
        return new ModelAndView("redirect:/admin/orders");
    }

    @PostMapping("/reject/{id}")
    public ModelAndView rejectOrder(@PathVariable Long id) {
        orderService.rejectOrder(id);
        return new ModelAndView("redirect:/admin/orders");
    }
}

package com.iphone_store.iphone_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.iphone_store.iphone_store.service.ProductService;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView homePageData() {
        ModelAndView mav = new ModelAndView("/index");

        mav.addObject("products", productService.getFirst10Products());
        return mav;
    }
}

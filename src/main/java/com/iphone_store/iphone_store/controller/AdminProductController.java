package com.iphone_store.iphone_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.iphone_store.iphone_store.entity.Product;
import com.iphone_store.iphone_store.service.CategoryService;
import com.iphone_store.iphone_store.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("products/list");
        mav.addObject("products", productService.getAllProducts());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView createForm() {
        ModelAndView mav = new ModelAndView("products/form");
        mav.addObject("product", new Product());
        mav.addObject("categories", categoryService.getAllCategories());
        return mav;
    }

    @PostMapping("/new")
    public ModelAndView save(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return new ModelAndView("redirect:/admin/products");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("products/form");
        mav.addObject("product", productService.getProductById(id));
        mav.addObject("categories", categoryService.getAllCategories());
        return mav;
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.saveProduct(product);
        return new ModelAndView("redirect:/admin/products");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ModelAndView("redirect:/admin/products");
    }
}

package com.iphone_store.iphone_store.controller;

import com.iphone_store.iphone_store.entity.Product;
import com.iphone_store.iphone_store.service.ProductService;
import com.iphone_store.iphone_store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

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
        mav.addObject("categories", categoryRepository.findAll());
        return mav;
    }

    @PostMapping
    public ModelAndView save(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("products/form");
        mav.addObject("product", productService.getProductById(id));
        mav.addObject("categories", categoryRepository.findAll());
        return mav;
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.saveProduct(product);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam String keyword) {
        ModelAndView mav = new ModelAndView("products/list");
        mav.addObject("products", productService.searchByName(keyword));
        return mav;
    }
}

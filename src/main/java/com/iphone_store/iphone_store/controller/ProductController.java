package com.iphone_store.iphone_store.controller;

import com.iphone_store.iphone_store.entity.Product;
import com.iphone_store.iphone_store.service.CategoryService;
import com.iphone_store.iphone_store.service.ProductService;
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
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("products/list");
        mav.addObject("products", productService.getFirst10Products());
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
        System.out.println(product);
        productService.saveProduct(product);
        return new ModelAndView("redirect:/products");
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
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ModelAndView mav = new ModelAndView("/search");
        var productPage = productService.searchByNamePaged(keyword, page, size);

        mav.addObject("products", productPage.getContent());
        mav.addObject("totalPages", productPage.getTotalPages());
        mav.addObject("currentPage", page);
        mav.addObject("keyword", keyword);

        return mav;
    }
}

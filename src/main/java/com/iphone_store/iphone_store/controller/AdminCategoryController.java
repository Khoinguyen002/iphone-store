package com.iphone_store.iphone_store.controller;

import com.iphone_store.iphone_store.entity.Category;
import com.iphone_store.iphone_store.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("categories/list");
        mav.addObject("categories", categoryService.getAllCategories());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView createForm() {
        ModelAndView mav = new ModelAndView("categories/form");
        mav.addObject("category", new Category());
        return mav;
    }

    @PostMapping("/new")
    public ModelAndView save(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return new ModelAndView("redirect:/admin/categories");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("categories/form");
        mav.addObject("category", categoryService.getCategoryById(id));
        return mav;
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id);
        categoryService.saveCategory(category);
        return new ModelAndView("redirect:/admin/categories");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ModelAndView("redirect:/admin/categories");
    }
}

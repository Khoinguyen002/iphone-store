package com.iphone_store.iphone_store.global;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MenuAdvice {

    @ModelAttribute
    public void addMenus(Model model) {
        List<Map<String, String>> menus = List.of(
                Map.of("title", "Home", "href", "/"),
                Map.of("title", "Products", "href", "/products"),
                Map.of("title", "Categories", "href", "/categories"),
                Map.of("title", "Create Product", "href", "/products/new"),
                Map.of("title", "Create Category", "href", "/categories/new"));

        model.addAttribute("menus", menus);
    }
}

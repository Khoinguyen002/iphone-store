package com.iphone_store.iphone_store.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MenuAdvice {

    @ModelAttribute
    public void addMenus(Model model) {
        List<Map<String, String>> menus = new ArrayList<>();
       

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && hasRole(auth, "ADMIN")) {
            menus.add(Map.of("title", "Products", "href", "/admin/products"));
            menus.add(Map.of("title", "Categories", "href", "/admin/categories"));
            menus.add(Map.of("title", "Orders", "href", "/admin/orders"));
            menus.add(Map.of("title", "Create Product", "href", "/admin/products/new"));
            menus.add(Map.of("title", "Create Category", "href", "/admin/categories/new"));
        } else {
            menus.add(Map.of("title", "Home", "href", "/"));
            menus.add(Map.of("title", "Search", "href", "/search"));
            menus.add(Map.of("title", "Cart", "href", "/cart"));
            menus.add(Map.of("title", "Orders", "href", "/orders"));
        }

        model.addAttribute("menus", menus);
    }

    private boolean hasRole(Authentication auth, String role) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
}

package com.iphone_store.iphone_store.controller;

import com.iphone_store.iphone_store.entity.Role;
import com.iphone_store.iphone_store.entity.User;
import com.iphone_store.iphone_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("auth/login");
    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        ModelAndView mav = new ModelAndView("auth/register");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("user") User user) {
        ModelAndView mav = new ModelAndView();

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            mav.setViewName("auth/register");
            mav.addObject("error", "Tên đăng nhập đã tồn tại");
            mav.addObject("user", user);
            return mav;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.MEMBER);
        userRepository.save(user);

        mav.setViewName("auth/login");
        mav.addObject("success", "Đăng ký thành công! Hãy đăng nhập.");
        return mav;
    }
}

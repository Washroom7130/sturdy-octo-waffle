package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        try {
            // Optional: check if username already exists
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM User WHERE username = ?",
                Integer.class,
                username
            );
            if (count != null && count > 0) {
                model.addAttribute("message", "Username already exists.");
                return "signup";
            }

            // Insert new user
            jdbcTemplate.update(
                "INSERT INTO User (username, password) VALUES (?, ?)",
                username, password
            );
            model.addAttribute("message", "Signup successful! You can now log in.");
        } catch (Exception e) {
            model.addAttribute("message", "Signup failed: " + e.getMessage());
        }
        return "signup";
    }
}

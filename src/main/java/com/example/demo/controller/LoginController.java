package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM User WHERE username = ? AND password = ?",
                Integer.class,
                username, password
            );

            if (count != null && count == 1) {
                // Success – store user in session
                session.setAttribute("username", username);
                return "redirect:/dashboard";
            } else {
                model.addAttribute("message", "Invalid username or password.");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Login failed: " + e.getMessage());
        }

        return "login";
    }
}

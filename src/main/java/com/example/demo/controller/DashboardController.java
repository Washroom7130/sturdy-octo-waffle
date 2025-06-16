package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DashboardController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login"; // Not logged in
        }

        model.addAttribute("username", username);

        List<Map<String, Object>> blogs = jdbcTemplate.queryForList(
                "SELECT id, title FROM Blog WHERE username = ? ORDER BY id DESC",
                username
        );
        model.addAttribute("blogs", blogs);
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // ✅ Clear session
        return "redirect:/login"; // 🔁 Redirect to login page
    }
}

package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

// import jakarta.persistence.EntityManager;
// import jakarta.persistence.PersistenceContext;
// import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;



@Controller
@RequestMapping
public class WebController {
    @GetMapping("/") 
    public String home() {
        return "index"; 
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "Guest") String name, 
                        @RequestParam(name = "age", required = false, defaultValue = "Unknown") String age, 
                        Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "hello";
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/db_status")
    public String checkDbStatus(Model model) {
        try {
            String result = jdbcTemplate.queryForObject("SELECT 1", String.class);
            model.addAttribute("status", "Database connection successful. Result: " + result);
        } catch (Exception e) {
            model.addAttribute("status", "Database connection failed: " + e.getMessage());
        }
        return "db_status";
    }
}
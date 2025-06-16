package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

@Controller
public class BlogController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/create_blog")
    public String showCreateBlogForm(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        return "create_blog";
    }

    @PostMapping("/create_blog")
    public String createBlog(@RequestParam String title,
                             @RequestParam String content,
                             HttpSession session,
                             Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        try {
            jdbcTemplate.update("INSERT INTO Blog (title, username, content) VALUES (?, ?, ?)",
                    title, username, content);
            return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Error creating blog: " + e.getMessage());
            return "create_blog";
        }
    }

    @GetMapping("/blog/{id}")
    public String viewBlog(@PathVariable int id, Model model) {
        try {
            Map<String, Object> blog = jdbcTemplate.queryForMap(
                    "SELECT * FROM Blog WHERE id = ?",
                    id
            );
            model.addAttribute("blog", blog);
            return "view_blog";
        } catch (Exception e) {
            model.addAttribute("error", "Blog not found.");
            return "view_blog";
        }
    }

    @GetMapping("/edit_blog/{id}")
    public String editBlogForm(@PathVariable int id, HttpSession session, Model model) {
        String currentUser = (String) session.getAttribute("username");
        Map<String, Object> blog = jdbcTemplate.queryForMap("SELECT * FROM Blog WHERE id = ?", id);

        if (!blog.get("username").equals(currentUser)) {
            return "redirect:/dashboard"; // not the author
        }

        model.addAttribute("blog", blog);
        return "edit_blog"; // create this template next
    }

    @PostMapping("/edit_blog/{id}")
    public String editBlogSubmit(@PathVariable int id,
                                 @RequestParam String title,
                                 @RequestParam String content,
                                 HttpSession session) {
        String currentUser = (String) session.getAttribute("username");
        Map<String, Object> blog = jdbcTemplate.queryForMap("SELECT * FROM Blog WHERE id = ?", id);

        if (!blog.get("username").equals(currentUser)) {
            return "redirect:/dashboard"; // not the author
        }

        jdbcTemplate.update("UPDATE Blog SET title = ?, content = ? WHERE id = ?",
                title, content, id);

        return "redirect:/blog/" + id;
    }

    @PostMapping("/delete_blog/{id}")
    public String deleteBlog(@PathVariable int id, HttpSession session) {
        String currentUser = (String) session.getAttribute("username");
        Map<String, Object> blog = jdbcTemplate.queryForMap("SELECT * FROM Blog WHERE id = ?", id);

        if (!blog.get("username").equals(currentUser)) {
            return "redirect:/dashboard"; // not the author
        }

        jdbcTemplate.update("DELETE FROM Blog WHERE id = ?", id);
        return "redirect:/dashboard";
    }
}

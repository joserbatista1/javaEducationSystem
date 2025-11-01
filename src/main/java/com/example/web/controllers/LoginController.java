package com.example.web.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Assume Subject is a simple POJO: id, code, name, instructor, credits, studentCount

@Controller
public class LoginController {

    // Serves the custom login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Maps to src/main/resources/templates/login.html
    }

    // Protected Home Page (User must have an active session to reach this)
    @GetMapping("/")
    public String homePage() {
        return "index"; // Maps to src/main/resources/templates/welcome.html
    }
    // NOTE: Remove your old @PostMapping("/login") method entirely!
}
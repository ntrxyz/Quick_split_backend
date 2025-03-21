package com.billsplitter.controller;


import com.billsplitter.model.User;
import com.billsplitter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String token = authService.registerUser(user);

        // Return JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        String token = authService.loginUser(email, password);

        // Fetch the user from the database using email (assuming you have a method in AuthService)
        User user = authService.getUserByEmail(email);
        String userId = (user != null) ? user.getId() : null; // Assuming getId() method exists in User model

        // Return JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful!");
        response.put("token", token);
        response.put("userId", userId);

        return ResponseEntity.ok(response);
    }


}

package com.billsplitter.controller;


import com.billsplitter.model.User;
import com.billsplitter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String token = authService.registerUser(user);
        return ResponseEntity.ok("User registered successfully! Token: " + token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        String token = authService.loginUser(email, password);
        return ResponseEntity.ok("Login successful! Token: " + token);
    }
}

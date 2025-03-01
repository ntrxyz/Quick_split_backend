package com.billsplitter.controller;

import com.billsplitter.model.User;
import com.billsplitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 🔹 GET user profile by ID
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 UPDATE user profile
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = existingUser.get();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully!");
    }

    // 🔹 DELETE user account
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(userId);
        return ResponseEntity.ok("User deleted successfully!");
    }
}

package com.example.lms.controller;

import com.example.lms.dto.CreateUserRequest;
import com.example.lms.dto.UpdateUserRequest;
import com.example.lms.dto.UserResponse;
import com.example.lms.mapper.UserMapper;
import com.example.lms.model.User;
import com.example.lms.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepo.findById(id);
        return user.map(userMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return user.map(userMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userMapper.toEntity(request);
        User savedUser = userRepo.save(user);
        return ResponseEntity.ok(userMapper.toResponse(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        User existingUser = userRepo.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        User updatedUser = userMapper.toEntity(request, existingUser);
        User savedUser = userRepo.save(updatedUser);
        return ResponseEntity.ok(userMapper.toResponse(savedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiResponse;
import com.ecommerce.backend.dto.AuthRequest;
import com.ecommerce.backend.dto.AuthResponse;
import com.ecommerce.backend.dto.RegisterRequest;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok("Login successful", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.ok("User registered successfully", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getCurrentUser() {
        User user = authService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.ok("Current user profile", user));
    }
}

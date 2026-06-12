package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.RegisterRequest;
import org.example.model.entity.User;
import org.example.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequest request) {
        User user = authService.register(request);
        return "Пользователь " + user.getEmail() + " успешно зарегистрирован (роль " + user.getRole() + ")";
    }

    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerAdmin(@RequestBody @Valid RegisterRequest request) {
        User user = authService.registerAdmin(request);
        return "Администратор " + user.getEmail() + " успешно зарегистрирован";
    }
}

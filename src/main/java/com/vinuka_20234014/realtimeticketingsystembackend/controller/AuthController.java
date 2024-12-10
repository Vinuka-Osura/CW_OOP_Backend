package com.vinuka_20234014.realtimeticketingsystembackend.controller;

import com.vinuka_20234014.realtimeticketingsystembackend.dto.LoginRequest;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.User;
import com.vinuka_20234014.realtimeticketingsystembackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setRole("customer");
        authService.registerUser(user);
        return "Registration successful";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

}

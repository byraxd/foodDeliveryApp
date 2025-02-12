package com.example.foodDeliveryApp.jwt.controller;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.jwt.dto.LoginDto;
import com.example.foodDeliveryApp.jwt.dto.RegisterDto;
import com.example.foodDeliveryApp.jwt.response.LoginResponse;
import com.example.foodDeliveryApp.jwt.service.impl.AuthService;
import com.example.foodDeliveryApp.jwt.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Client> register(@RequestBody RegisterDto registerDto) {
        Client client = authService.signUp(registerDto);

        return ResponseEntity.ok(client);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto) {
        Client authenticatedClient = authService.authenticate(loginDto);

        String jwtToken = jwtService.generateToken(authenticatedClient);

        LoginResponse loginResponse = LoginResponse.builder().expiresIn(jwtService.getExpirationTime()).token(jwtToken).build();
        return ResponseEntity.ok(loginResponse);
    }
}

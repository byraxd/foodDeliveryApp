package com.example.foodDeliveryApp.jwt.service.impl;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.repository.ClientRepository;
import com.example.foodDeliveryApp.jwt.dto.LoginDto;
import com.example.foodDeliveryApp.jwt.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "clients")
public class AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @CacheEvict(allEntries = true)
    public Client signUp(RegisterDto registerDto) {
        Client client = Client
                .builder()
                .username(registerDto.username())
                .password(passwordEncoder.encode(registerDto.password()))
                .email(registerDto.email())
                .phone(registerDto.phone())
                .walletBalance(0.00)
                .build();

        return clientRepository.save(client);
    }

    public Client authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));

        return clientRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}

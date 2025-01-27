package com.example.foodDeliveryApp.jwt.dto;

import lombok.Builder;

@Builder
public record RegisterDto(String email, String username, String password, String phone) {
}

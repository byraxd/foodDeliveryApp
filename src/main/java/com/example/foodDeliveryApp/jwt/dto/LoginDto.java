package com.example.foodDeliveryApp.jwt.dto;

import lombok.Builder;

@Builder
public record LoginDto(String email, String password) {
}

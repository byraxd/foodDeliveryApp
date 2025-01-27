package com.example.foodDeliveryApp.jwt.response;

import lombok.Builder;

@Builder
public record LoginResponse(long expiresIn, String token) {
}

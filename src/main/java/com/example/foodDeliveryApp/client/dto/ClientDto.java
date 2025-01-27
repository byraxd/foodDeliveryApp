package com.example.foodDeliveryApp.client.dto;

import lombok.Builder;

@Builder
public record ClientDto(
        String username,
        String password,
        String email,
        String phone,
        Double walletBalance,
        String role) {
}

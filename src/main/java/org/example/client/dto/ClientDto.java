package org.example.client.dto;

public record ClientDto(
        String username,
        String password,
        String email,
        String phone,
        Double walletBalance) {
}

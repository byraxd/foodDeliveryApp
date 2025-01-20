package com.example.foodDeliveryApp.feedback.dto;

public record FeedbackDto(
        Integer rating,
        String text, Long clientId,
        Long productId) {
}

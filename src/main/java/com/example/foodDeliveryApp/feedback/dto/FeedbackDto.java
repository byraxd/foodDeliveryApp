package com.example.foodDeliveryApp.feedback.dto;

import lombok.Builder;

@Builder
public record FeedbackDto(
        Integer rating,
        String text,
        Long clientId,
        Long productId) {
}

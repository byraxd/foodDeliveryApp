package com.example.foodDeliveryApp.order.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderDto(
        Long clientId,
        List<Long> productsId) {
}

package com.example.foodDeliveryApp.product.dto;

import com.example.foodDeliveryApp.product.model.ProductType;
import lombok.Builder;

@Builder
public record ProductDto(
        String name,
        ProductType productType,
        String description,
        Double price,
        Boolean available,
        String imageUrl) {
}

package org.example.product.dto;

import org.example.product.model.ProductType;

public record ProductDto(
        String name,
        ProductType type,
        String description,
        Double price,
        Boolean available,
        String imageUrl) {
}

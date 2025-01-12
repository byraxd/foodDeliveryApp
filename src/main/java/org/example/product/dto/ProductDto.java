package org.example.product.dto;

import lombok.Builder;
import org.example.product.model.ProductType;

@Builder
public record ProductDto(
        String name,
        ProductType type,
        String description,
        Double price,
        Boolean available,
        String imageUrl) {
}

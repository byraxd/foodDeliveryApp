package org.example.order.dto;

import java.util.List;

public record OrderDto(
        Long clientId,
        List<Long> productsId) {
}

package com.example.foodDeliveryApp.cart.repository;

import com.example.foodDeliveryApp.cart.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}

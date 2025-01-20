package com.example.foodDeliveryApp.product.repository;

import com.example.foodDeliveryApp.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

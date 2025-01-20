package com.example.foodDeliveryApp.product.service;


import com.example.foodDeliveryApp.product.dto.ProductDto;
import com.example.foodDeliveryApp.product.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getById(Long id);

    Product save(ProductDto productDto);

    Product updateById(Long id, ProductDto productDto);

    void deleteById(Long id);
}

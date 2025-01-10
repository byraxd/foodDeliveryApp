package org.example.product.service;

import org.example.product.dto.ProductDto;
import org.example.product.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getById(Long id);

    Product save(ProductDto productDto);

    Product updateById(Long id, ProductDto productDto);

    void deleteById(Long id);
}

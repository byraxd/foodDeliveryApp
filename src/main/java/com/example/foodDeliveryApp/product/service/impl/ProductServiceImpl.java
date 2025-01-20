package com.example.foodDeliveryApp.product.service.impl;

import com.example.foodDeliveryApp.exception.model.ProductNotFoundException;
import com.example.foodDeliveryApp.product.dto.ProductDto;
import com.example.foodDeliveryApp.product.model.Product;
import com.example.foodDeliveryApp.product.repository.ProductRepository;
import com.example.foodDeliveryApp.product.service.ProductService;
import com.example.foodDeliveryApp.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        log.info("Fetching all products");

        List<Product> products = productRepository.findAll();
        log.info("Successfully fetched list of all products: {}", products);

        return products;
    }

    @Override
    public Product getById(Long id) {
        log.info("Fetching product by id: {}", id);

        ValidateUtils.validateId(id);

        Product product = findProductById(id);
        log.info("Successfully fetched product: {}", product);

        return product;
    }

    @Override
    public Product save(ProductDto productDto) {
        log.info("Saving product: {}", productDto);

        ValidateUtils.validateDto(productDto);

        Product product = Product
                .builder()
                .name(productDto.name())
                .type(productDto.productType())
                .description(productDto.description())
                .price(productDto.price())
                .available(productDto.available())
                .imageUrl(productDto.imageUrl())
                .build();

        productRepository.save(product);
        log.info("Successfully saved product: {}", product);

        return product;
    }

    @Override
    @Transactional
    public Product updateById(Long id, ProductDto productDto) {
        log.info("Updating product: {}, by id: {}", productDto, id);

        ValidateUtils.validateId(id);
        ValidateUtils.validateDto(productDto);

        Product product = findProductById(id);

        product.setName(productDto.name());
        product.setType(productDto.productType());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setAvailable(productDto.available());
        product.setImageUrl(productDto.imageUrl());

        productRepository.save(product);
        log.info("Successfully updated product: {}", product);

        return product;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting product: {}", id);

        ValidateUtils.validateId(id);

        if(!productRepository.existsById(id)) {
            log.error("Product is not exists in database: {}", id);
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        productRepository.deleteById(id);
        log.info("Successfully deleted product: {}", id);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            log.error("Product with id {} not found", id);
            return new ProductNotFoundException("Product with id " + id + " not found");
        });
    }
}

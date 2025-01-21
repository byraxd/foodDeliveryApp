package com.example.foodDeliveryApp.product.service.impl;

import com.example.foodDeliveryApp.product.dto.ProductDto;
import com.example.foodDeliveryApp.product.model.Product;
import com.example.foodDeliveryApp.product.model.ProductType;
import com.example.foodDeliveryApp.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product firstProduct;

    private Product secondProduct;

    @BeforeEach
    void SetUp(){
        firstProduct = Product
                .builder()
                .id(1L)
                .name("First product")
                .type(ProductType.PIZZA)
                .description("First product")
                .price(10.0)
                .build();

        secondProduct = Product
                .builder()
                .id(2L)
                .name("Second product")
                .type(ProductType.BBQ)
                .description("Second product")
                .price(20.0)
                .build();
    }

    @Test
    void test_getAll_shouldReturnAllProducts(){

        Mockito.when(productRepository.findAll()).thenReturn(List.of(firstProduct, secondProduct));

        List<Product> products = productService.getAll();

        Assertions.assertEquals(2, products.size());

        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    void test_getById_shouldReturnProduct_ifProductExists(){
        Long id = 1L;

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(firstProduct));

        Product product = productService.getById(id);

        Assertions.assertNotNull(product);
        Assertions.assertEquals(id, product.getId());
        Assertions.assertEquals(firstProduct, product);

        Mockito.verify(productRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void test_save_shouldSaveAndReturnProduct_IfDtoIsValid(){
        ProductDto productDto = ProductDto
                .builder()
                .name("First product")
                .productType(ProductType.PIZZA)
                .description("First product")
                .price(10.0)
                .build();

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(firstProduct);

        Product product = productService.save(productDto);

        Assertions.assertNotNull(product);
        Assertions.assertEquals(firstProduct.getName(), product.getName());
        Assertions.assertEquals(firstProduct.getType(), product.getType());
        Assertions.assertEquals(firstProduct.getDescription(), product.getDescription());
        Assertions.assertEquals(firstProduct.getPrice(), product.getPrice());

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    void test_updateById_shouldUpdateProductById_ifProductExists(){
        Long id = 1L;

        ProductDto productDto = ProductDto
                .builder()
                .name("First product{EDIT}")
                .productType(ProductType.PIZZA)
                .description("First product{EDIT}")
                .price(10.0)
                .build();

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(firstProduct));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(firstProduct);

        Product product = productService.updateById(id, productDto);

        Assertions.assertNotNull(product);
        Assertions.assertEquals(firstProduct.getName(), product.getName());
        Assertions.assertEquals(firstProduct.getType(), product.getType());
        Assertions.assertEquals(firstProduct.getDescription(), product.getDescription());
        Assertions.assertEquals(firstProduct.getPrice(), product.getPrice());

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    void test_deleteById_shouldDeleteProductById_ifProductExists(){
        Long id = 1L;

        Mockito.when(productRepository.existsById(id)).thenReturn(true);

        productService.deleteById(id);

        Mockito.verify(productRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(id);

    }
}

package org.example.product.repository;

import org.example.product.model.Product;
import org.example.product.model.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product
                .builder()
                .name("Product Name")
                .type(ProductType.PIZZA)
                .description("Product Description")
                .price(10.00)
                .available(true)
                .imageUrl("Product Image")
                .build();

    }
    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void givenProductObject_whenSave_thenReturnSavedProduct() {
        Product savedProduct = productRepository.save(product);

        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(product.getName(), savedProduct.getName());
        Assertions.assertEquals(product.getDescription(), savedProduct.getDescription());
        Assertions.assertEquals(product.getPrice(), savedProduct.getPrice());
    }

    @Test
    void givenProductObject_whenFindById_thenReturnProduct() {
        Product savedProduct = productRepository.save(product);

        Product foundedProduct = productRepository.findById(savedProduct.getId()).get();

        Assertions.assertNotNull(foundedProduct);
        Assertions.assertEquals(product.getName(), savedProduct.getName());
        Assertions.assertEquals(product.getDescription(), savedProduct.getDescription());
        Assertions.assertEquals(product.getPrice(), savedProduct.getPrice());
    }

    @Test
    void givenProductObject_whenFindAll_thenReturnAllProducts() {
        Product savedProduct = productRepository.save(product);

        List<Product> savedProducts = productRepository.findAll();

        Assertions.assertNotNull(savedProducts);
        Assertions.assertEquals(1, savedProducts.size());
        Assertions.assertEquals(savedProduct.getName(), savedProducts.get(0).getName());
    }

    @Test
    void givenProductObject_whenDelete() {
        Product savedProduct = productRepository.save(product);

        productRepository.delete(savedProduct);

        List<Product> savedProducts = productRepository.findAll();

        Assertions.assertNotNull(savedProducts);
        Assertions.assertEquals(0, savedProducts.size());
    }

    @Test
    void givenProductObject_whenUpdate_thenReturnUpdatedProduct() {
        Product savedProduct = productRepository.save(product);

        Product foundedProduct = productRepository.findById(product.getId()).get();
        savedProduct.setDescription("Updated Description");
        Product updatedProduct = productRepository.save(savedProduct);

        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals(savedProduct.getName(), updatedProduct.getName());

    }
}

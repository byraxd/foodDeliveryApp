package com.example.foodDeliveryApp.product.controller;

import com.example.foodDeliveryApp.product.dto.ProductDto;
import com.example.foodDeliveryApp.product.model.Product;
import com.example.foodDeliveryApp.product.model.ProductType;
import com.example.foodDeliveryApp.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product
                .builder()
                .name("First product")
                .type(ProductType.PIZZA)
                .description("First product")
                .price(10.0)
                .build();
    }

    @Test
    void test_getAllProducts() throws Exception {
        Mockito.when(productService.getAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(product.getName()))
                .andExpect(jsonPath("$[0].type").value(product.getType().toString()))
                .andExpect(jsonPath("$[0].description").value(product.getDescription()))
                .andExpect(jsonPath("$[0].price").value(product.getPrice()));

        Mockito.verify(productService, Mockito.times(1)).getAll();

    }
    @Test
    void test_getProductById() throws Exception {
        Long id = 1L;

        Mockito.when(productService.getById(id)).thenReturn(product);

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.type").value(product.getType().toString()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));

        Mockito.verify(productService, Mockito.times(1)).getById(id);
    }

    @Test
    void test_saveProduct() throws Exception {
        String productJson = """
        {
            "name": "First product",
            "productType": "PIZZA",
            "description": "First product",
            "price": 10.0
        }
    """;

        Mockito.when(productService.save(Mockito.any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType("application/json")
                .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.type").value(product.getType().toString()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));

        Mockito.verify(productService, Mockito.times(1)).save(Mockito.any(ProductDto.class));
    }

    @Test
    void test_updateProduct() throws Exception {
        Long id = 1L;

        String productJson = """
        {
            "name": "First product{Updated}",
            "productType": "PIZZA",
            "description": "First product{Updated}",
            "price": 10.0
        }
    """;

        product.setName("First product{Updated}");
        product.setType(ProductType.PIZZA);
        product.setDescription("First product{Updated}");
        product.setPrice(10.0);

        Mockito.when(productService.updateById(Mockito.eq(id), Mockito.any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/{id}", id)
                        .contentType("application/json")
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()));

        Mockito.verify(productService, Mockito.times(1)).updateById(Mockito.eq(id), Mockito.any(ProductDto.class));
    }

    @Test
    void test_deleteProduct() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/products/{id}", id)
        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}

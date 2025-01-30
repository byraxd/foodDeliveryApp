package com.example.foodDeliveryApp.order.controller;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.jwt.service.impl.JwtService;
import com.example.foodDeliveryApp.order.dto.OrderDto;
import com.example.foodDeliveryApp.order.model.Order;
import com.example.foodDeliveryApp.order.service.OrderService;
import com.example.foodDeliveryApp.product.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private JwtService jwtService;

    private Order order;

    @BeforeEach
    public void setUp() throws Exception {
        this.order = Order
                .builder()
                .client(Client.builder().username("client username").email("client email").walletBalance(300.00).build())
                .paid(false)
                .products(List.of(Product.builder().name("First Product").price(10.00).build(), Product.builder().name("Second Product").price(20.00).build()))
                .totalPrice(30.00)
                .build();
    }

    @Test
    void test_getOrders() throws Exception {
        Mockito.when(orderService.getAll()).thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].paid").value(false))
                .andExpect(jsonPath("$[0].totalPrice").value(30.00));

        Mockito.verify(orderService, Mockito.times(1)).getAll();
    }

    @Test
    void test_getOrderById() throws Exception {
        Mockito.when(orderService.getById(Mockito.anyLong())).thenReturn(order);

        mockMvc.perform(get("/api/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paid").value(false))
                .andExpect(jsonPath("$.totalPrice").value(30.00));

        Mockito.verify(orderService, Mockito.times(1)).getById(Mockito.anyLong());
    }

    @Test
    void test_createOrder() throws Exception {
        Order orderForJson = Order
                .builder()
                .client(Client.builder().username("client username").email("client email").build())
                .paid(false)
                .products(List.of(Product.builder().name("First Product").price(10.00).build(), Product.builder().name("Second Product").price(20.00).build()))
                .totalPrice(30.00)
                .build();
        String orderJson = objectMapper.writeValueAsString(orderForJson);

        Mockito.when(orderService.save(Mockito.any(OrderDto.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paid").value(false))
                .andExpect(jsonPath("$.totalPrice").value(30.00));

        Mockito.verify(orderService, Mockito.times(1)).save(Mockito.any(OrderDto.class));
    }

    @Test
    void test_updateOrder() throws Exception {
        Long id = 1L;

        Order orderForJson = Order
                .builder()
                .client(Client.builder().username("client username").email("client email").build())
                .paid(true)
                .products(List.of(Product.builder().name("First Product").price(10.00).build(), Product.builder().name("Second Product").price(20.00).build()))
                .totalPrice(10.00)
                .build();
        String orderJson = objectMapper.writeValueAsString(orderForJson);

        order.setPaid(true);
        order.setTotalPrice(10.00);

        Mockito.when(orderService.updateById(Mockito.eq(id), Mockito.any(OrderDto.class))).thenReturn(order);

        mockMvc.perform(put("/api/orders/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paid").value(true))
                .andExpect(jsonPath("$.totalPrice").value(10.00));

        Mockito.verify(orderService, Mockito.times(1)).updateById(Mockito.eq(id), Mockito.any(OrderDto.class));
    }

    @Test
    void test_deleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/{id}", 1L))
                .andExpect(status().isOk());

        Mockito.verify(orderService, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void test_payForOrder() throws Exception {
        order.setPaid(true);
        Mockito.when(orderService.payForOrderById(Mockito.anyLong())).thenReturn(order);

        mockMvc.perform(put("/api/orders/{id}/pay", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paid").value(true));

        Mockito.verify(orderService, Mockito.times(1)).payForOrderById(Mockito.anyLong());
    }
}

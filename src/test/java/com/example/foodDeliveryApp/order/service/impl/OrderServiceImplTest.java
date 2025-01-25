package com.example.foodDeliveryApp.order.service.impl;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.repository.ClientRepository;
import com.example.foodDeliveryApp.order.dto.OrderDto;
import com.example.foodDeliveryApp.order.model.Order;
import com.example.foodDeliveryApp.order.repository.OrderRepository;
import com.example.foodDeliveryApp.product.model.Product;
import com.example.foodDeliveryApp.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order firstOrder;
    private Order secondOrder;

    @BeforeEach
    public void setUp() {
        firstOrder = Order
                .builder()
                .client(Client.builder().username("Client of first order").walletBalance(100.00).build())
                .products(List.of(Product.builder().name("Product of the first order").price(10.00).build()))
                .createdAt(Instant.now())
                .paid(false)
                .totalPrice(10.00)
                .build();

        secondOrder = Order
                .builder()
                .client(Client.builder().username("Client of second order").build())
                .products(List.of(Product.builder().name("Product of the second order").price(10.00).build()))
                .createdAt(Instant.now())
                .paid(false)
                .totalPrice(10.00)
                .build();
    }

    @Test
    void test_getAllOrders() {
        List<Order> orders = List.of(firstOrder, secondOrder);

        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAll();

        Assertions.assertEquals(orders, result);

        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
    }

    @Test
    void test_getOrderById(){
        Long id = 1L;

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(firstOrder));

        Order result = orderService.getById(id);

        Assertions.assertEquals(firstOrder, result);

        Mockito.verify(orderRepository, Mockito.times(1)).findById(id);

    }

    @Test
    void test_saveOrder(){
        OrderDto orderDto = OrderDto
                .builder()
                .clientId(1L)
                .productsId(List.of(1L))
                .build();

        Mockito.when(clientRepository.findById(orderDto.clientId())).thenReturn(Optional.of(firstOrder.getClient()));
        Mockito.when(productRepository.findAllById(orderDto.productsId())).thenReturn(firstOrder.getProducts());

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(firstOrder);

        Order result = orderService.save(orderDto);

        Assertions.assertEquals(firstOrder.getClient(), result.getClient());
        Assertions.assertEquals(firstOrder.getProducts(), result.getProducts());
        Assertions.assertEquals(firstOrder.getTotalPrice(), result.getTotalPrice());

        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));
    }

    @Test
    void test_updateOrder(){
        Long id = 1L;

        OrderDto orderDto = OrderDto
                .builder()
                .clientId(1L)
                .productsId(List.of(1L))
                .build();

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(firstOrder));
        Mockito.when(clientRepository.findById(orderDto.clientId())).thenReturn(Optional.of(firstOrder.getClient()));
        Mockito.when(productRepository.findAllById(orderDto.productsId())).thenReturn(firstOrder.getProducts());

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(firstOrder);

        Order result = orderService.updateById(id, orderDto);

        Assertions.assertEquals(firstOrder.getClient(), result.getClient());
        Assertions.assertEquals(firstOrder.getProducts(), result.getProducts());
        Assertions.assertEquals(firstOrder.getTotalPrice(), result.getTotalPrice());

        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));
    }

    @Test
    void test_deleteOrder(){
        Long id = 1L;

        Mockito.when(orderRepository.existsById(id)).thenReturn(true);

        orderService.deleteById(id);

        Mockito.verify(orderRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(id);

    }

    @Test
    void test_payForOrderById(){
        Long id = 1L;
        Double walletBalance = firstOrder.getClient().getWalletBalance();

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(firstOrder));
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(firstOrder.getClient());
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(firstOrder);

        Order result = orderService.payForOrderById(id);

        Assertions.assertEquals(result.getClient().getWalletBalance(), walletBalance - firstOrder.getTotalPrice());
        Assertions.assertEquals(result.getPaid(), true);

        Mockito.verify(orderRepository, Mockito.times(1)).findById(id);
        Mockito.verify(clientRepository, Mockito.times(1)).save(Mockito.any(Client.class));
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));

    }

}

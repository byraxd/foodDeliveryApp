package com.example.foodDeliveryApp.order.repository;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.repository.ClientRepository;
import com.example.foodDeliveryApp.order.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Order order;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = Client
                .builder()
                .username("Client username")
                .password("Client password")
                .email("email@test.com")
                .phone("123456789")
                .walletBalance(10.00)
                .build();

        clientRepository.save(client);

        order = Order
                .builder()
                .client(client)
                .totalPrice(10.00)
                .paid(false)
                .createdAt(Instant.now())
                .build();
    }

    @AfterEach
    public void tearDown() {
        orderRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void givenOrderObject_whenFindAll_thenReturnAllOrders() {
        Order savedOrder = orderRepository.save(order);

        List<Order> orders = orderRepository.findAll();

        Assertions.assertNotNull(orders);
        Assertions.assertEquals(orders.get(0).getTotalPrice(), savedOrder.getTotalPrice());
    }

    @Test
    void givenOrderObject_whenFindById_thenReturnOrder(){
        Order savedOrder = orderRepository.save(order);

        Order foundOrder = orderRepository.findById(savedOrder.getId()).get();

        Assertions.assertNotNull(foundOrder);
        Assertions.assertEquals(foundOrder.getTotalPrice(), savedOrder.getTotalPrice());
    }

    @Test
    void givenOrderObject_whenSave_thenReturnSavedOrder(){
        Order savedOrder = orderRepository.save(order);

        Order foundedSavedOrder = orderRepository.findById(savedOrder.getId()).get();

        Assertions.assertNotNull(foundedSavedOrder);
        Assertions.assertEquals(foundedSavedOrder.getTotalPrice(), savedOrder.getTotalPrice());
    }

    @Test
    void giveOrderObject_whenUpdate_thenReturnUpdatedOrder(){
        Order savedOrder = orderRepository.save(order);

        Order foundOrder = orderRepository.findById(savedOrder.getId()).get();

        foundOrder.setTotalPrice(savedOrder.getTotalPrice()+10.00);
        foundOrder.setPaid(true);

        orderRepository.save(foundOrder);

        Order updatedOrder = orderRepository.findById(foundOrder.getId()).get();

        Assertions.assertNotNull(updatedOrder);
        Assertions.assertEquals(updatedOrder.getTotalPrice(), savedOrder.getTotalPrice());
    }

    @Test
    void givenOrderObject_whenDelete(){
        Order savedOrder = orderRepository.save(order);

        Long id = savedOrder.getId();

        List<Order> allOrdersBeforeDeletion = orderRepository.findAll();

        orderRepository.deleteById(id);

        List<Order> allOrdersAfterDeletion = orderRepository.findAll();

        Assertions.assertTrue(allOrdersAfterDeletion.size() < allOrdersBeforeDeletion.size());
    }
}

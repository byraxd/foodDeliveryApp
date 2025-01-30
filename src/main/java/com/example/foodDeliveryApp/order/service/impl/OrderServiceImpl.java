package com.example.foodDeliveryApp.order.service.impl;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.repository.ClientRepository;
import com.example.foodDeliveryApp.exception.model.ClientNotFoundException;
import com.example.foodDeliveryApp.exception.model.NotEnoughMoneyExceptions;
import com.example.foodDeliveryApp.exception.model.OrderNotFoundException;
import com.example.foodDeliveryApp.order.dto.OrderDto;
import com.example.foodDeliveryApp.order.model.Order;
import com.example.foodDeliveryApp.order.repository.OrderRepository;
import com.example.foodDeliveryApp.order.service.OrderService;
import com.example.foodDeliveryApp.product.model.Product;
import com.example.foodDeliveryApp.product.repository.ProductRepository;
import com.example.foodDeliveryApp.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@CacheConfig(cacheNames = "orders")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Cacheable
    public List<Order> getAll() {
        log.info("Fetching all orders");

        List<Order> orders = orderRepository.findAll();
        log.info("Successfully fetched list of all orders: {}", orders);

        return orders;
    }

    @Override
    @Cacheable(key = "#id")
    public Order getById(Long id) {
        log.info("Fetching order by id: {}", id);

        ValidateUtils.validateId(id);

        Order order = findOrderById(id);
        log.info("Successfully fetched order by id: {}", order);

        return order;
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public Order save(OrderDto orderDto) {
        log.info("Saving order: {}", orderDto);

        ValidateUtils.validateDto(orderDto);

        List<Product> products = productRepository.findAllById(orderDto.productsId());

        Order order = Order
                .builder()
                .client(findClientByClientId(orderDto.clientId()))
                .products(products)
                .paid(false)
                .totalPrice(products.stream().mapToDouble(Product::getPrice).sum())
                .createdAt(Instant.now())
                .build();

        orderRepository.save(order);
        log.info("Successfully saved order: {}", order);

        return order;
    }

    @Override
    @Transactional
    @Caching(put = {@CachePut(key = "#result.getId()")}, evict = {@CacheEvict(allEntries = true)})
    public Order updateById(Long id, OrderDto orderDto) {
        log.info("Updating order {}, by id: {}", orderDto, id);

        ValidateUtils.validateId(id);
        ValidateUtils.validateDto(orderDto);

        Order order = findOrderById(id);

        List<Product> products = productRepository.findAllById(orderDto.productsId());

        order.setClient(findClientByClientId(orderDto.clientId()));
        order.setProducts(products);
        order.setPaid(false);
        order.setTotalPrice(products.stream().mapToDouble(Product::getPrice).sum());

        orderRepository.save(order);
        log.info("Successfully updated order by id: {}", order);

        return order;
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteById(Long id) {
        log.info("Deleting order: {}", id);

        ValidateUtils.validateId(id);

        if (!orderRepository.existsById(id)) {
            log.error("Order is not exists in database: {}", id);
            throw new OrderNotFoundException("Order with id " + id + " not found");
        }

        orderRepository.deleteById(id);
        log.info("Successfully deleted order: {}", id);
    }

    @Override
    @Transactional
    @Caching(put = {@CachePut(key = "#result.getId()")}, evict = {@CacheEvict(allEntries = true)})
    public Order payForOrderById(Long id) {
        log.info("Paying order by id: {}", id);

        ValidateUtils.validateId(id);

        Order order = findOrderById(id);
        Client client = order.getClient();

        if (client.getWalletBalance() < order.getTotalPrice()) {
            log.error("Paying order by client balance less than total price");
            throw new NotEnoughMoneyExceptions("Client doesnt have enough money: " + client);
        }

        client.setWalletBalance(client.getWalletBalance() - order.getTotalPrice());
        order.setPaid(true);

        clientRepository.save(client);
        log.info("Client saved successfully: {}", client);

        orderRepository.save(order);
        log.info("Successfully payed order: {}", order);

        return order;
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> {
            log.error("Order with id {} not found", id);
            return new OrderNotFoundException("Order with id " + id + " not found");
        });
    }

    private Client findClientByClientId(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> {
            log.error("Client with id {} not found", clientId);
            return new ClientNotFoundException("Client with id " + clientId + " not found");
        });
    }
}

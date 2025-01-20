package com.example.foodDeliveryApp.order.service;

import com.example.foodDeliveryApp.order.dto.OrderDto;
import com.example.foodDeliveryApp.order.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    Order getById(Long id);

    Order save(OrderDto orderDto);

    Order updateById(Long id, OrderDto orderDto);

    void deleteById(Long id);

    Order payForOrderById(Long id);

}

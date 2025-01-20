package com.example.foodDeliveryApp.order.repository;

import com.example.foodDeliveryApp.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}

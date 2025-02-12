package com.example.foodDeliveryApp.cart.repository;

import com.example.foodDeliveryApp.cart.model.Cart;
import com.example.foodDeliveryApp.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    Cart findCartByClient(Client client);
}

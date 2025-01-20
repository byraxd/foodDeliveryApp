package com.example.foodDeliveryApp.client.repository;

import com.example.foodDeliveryApp.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}

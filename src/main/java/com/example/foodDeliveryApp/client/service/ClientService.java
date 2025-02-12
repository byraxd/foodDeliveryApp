package com.example.foodDeliveryApp.client.service;


import com.example.foodDeliveryApp.client.dto.ClientDto;
import com.example.foodDeliveryApp.client.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client getById(Long id);

    Client save(ClientDto clientDto);

    Client updateById(Long id, ClientDto clientDto);

    void deleteById(Long id);

    Client findByEmail(String email);
}

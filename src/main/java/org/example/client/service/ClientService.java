package org.example.client.service;

import org.example.client.dto.ClientDto;
import org.example.client.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client getById(Long id);

    Client save(ClientDto clientDto);

    Client updateById(Long id, ClientDto clientDto);

    void deleteById(Long id);
}

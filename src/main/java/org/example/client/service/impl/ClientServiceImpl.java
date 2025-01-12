package org.example.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.client.dto.ClientDto;
import org.example.client.model.Client;
import org.example.client.repository.ClientRepository;
import org.example.client.service.ClientService;
import org.example.exception.model.ClientNotFoundException;
import org.example.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        log.info("Fetching all clients");

        List<Client> clients = clientRepository.findAll();
        log.info("Successfully fetched list of all clients: {}", clients);

        return clients;
    }

    @Override
    public Client getById(Long id) {
        log.info("Fetching client with id: {}", id);

        ValidateUtils.validateId(id);

        Client client = findClientById(id);
        log.info("Successfully fetched client: {}", client);

        return client;
    }

    @Override
    public Client save(ClientDto clientDto) {
        log.info("Saving client: {}", clientDto);

        ValidateUtils.validateDto(clientDto);

        Client client = Client
                .builder()
                .username(clientDto.username())
                .password(clientDto.password())
                .email(clientDto.email())
                .phone(clientDto.phone())
                .walletBalance(clientDto.walletBalance())
                .build();

        clientRepository.save(client);
        log.info("Successfully saved client: {}", client);

        return client;
    }

    @Override
    @Transactional
    public Client updateById(Long id, ClientDto clientDto) {
        log.info("Updating client: {}, by id: {}", clientDto, id);

        ValidateUtils.validateId(id);
        ValidateUtils.validateDto(clientDto);

        Client client = findClientById(id);

        client.setUsername(clientDto.username());
        client.setPassword(clientDto.password());
        client.setEmail(clientDto.email());
        client.setPhone(clientDto.phone());
        client.setWalletBalance(clientDto.walletBalance());

        clientRepository.save(client);
        log.info("Successfully updated client: {}", client);

        return client;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting client: {}", id);

        ValidateUtils.validateId(id);

        if(!clientRepository.existsById(id)) {
            log.error("Client is not exists in database: {}", id);
            throw new ClientNotFoundException("Client with id " + id + " not found");
        }

        clientRepository.deleteById(id);
        log.info("Successfully deleted client: {}", id);
    }

    private Client findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> {
            log.error("Client with id {} not found", id);
            return new ClientNotFoundException("Client with id " + id + " not found");
        });
    }
}

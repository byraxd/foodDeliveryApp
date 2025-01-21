package com.example.foodDeliveryApp.client.repository;


import com.example.foodDeliveryApp.client.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

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
    }

    @AfterEach
    public void tearDown() {
        clientRepository.deleteAll();
    }

    @Test
    void givenClientObject_whenSave_thenReturnSavedClient() {
        Client savedClient = clientRepository.save(client);

        Client foundClient = clientRepository.findById(savedClient.getId()).get();

        Assertions.assertNotNull(foundClient);
        Assertions.assertEquals(savedClient.getUsername(), foundClient.getUsername());
        Assertions.assertEquals(savedClient.getPassword(), foundClient.getPassword());
        Assertions.assertEquals(savedClient.getEmail(), foundClient.getEmail());
    }

    @Test
    void giveClientObject_whenFindById_thenReturnClient() {
        Client savedClient = clientRepository.save(client);

        Client result = clientRepository.findById(savedClient.getId()).get();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedClient.getUsername(), result.getUsername());
        Assertions.assertEquals(savedClient.getPassword(), result.getPassword());
        Assertions.assertEquals(savedClient.getEmail(), result.getEmail());
    }

    @Test
    void givenClientObject_whenFindAll_thenReturnAllClients() {
        Client savedClient =clientRepository.save(client);

        List<Client> clients = clientRepository.findAll();

        Assertions.assertNotNull(clients);
        Assertions.assertEquals(1, clients.size());
        Assertions.assertEquals(savedClient.getUsername(), clients.get(0).getUsername());

    }

    @Test
    void givenClientObject_whenDelete() {
        Client savedClient = clientRepository.save(client);

        clientRepository.delete(savedClient);

        List<Client> savedClients = clientRepository.findAll();

        Assertions.assertNotNull(savedClients);
        Assertions.assertEquals(0, savedClients.size());
    }

    @Test
    void givenClientObject_whenUpdate_thenReturnUpdatedClient() {
        Client savedClient = clientRepository.save(client);

        Client clientForUpdate = clientRepository.findById(savedClient.getId()).get();

        clientForUpdate.setUsername("updatedUsername");
        clientForUpdate.setPassword("updatedPassword");

        clientRepository.save(clientForUpdate);

        Client result = clientRepository.findById(savedClient.getId()).get();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedClient.getId(), result.getId());
        Assertions.assertEquals(result.getUsername(), savedClient.getUsername());
    }
}

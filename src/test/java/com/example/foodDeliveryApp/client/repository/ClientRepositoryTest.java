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
                .id(1L)
                .username("test")
                .password("test")
                .email("test@test.com")
                .phone("123456789")
                .walletBalance(10.00)
                .build();
    }

    @AfterEach
    public void tearDown() {
        clientRepository.deleteAll();
    }

    @Test
    void test_findAll_shouldReturnAllClients() {
        clientRepository.save(client);

        List<Client> clients = clientRepository.findAll();

        Assertions.assertNotNull(clients);
        Assertions.assertEquals(1, clients.size());

    }

    @Test
    void test_findById_shouldReturnClient() {
        Client savedClient = clientRepository.save(client);

        Client result = clientRepository.findById(savedClient.getId()).get();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedClient.getId(), result.getId());
    }

    @Test
    void test_save_shouldSaveClient() {
        Client savedClient = clientRepository.save(client);

        Client result = clientRepository.findById(savedClient.getId()).get();

        Assertions.assertNotNull(result);

        Assertions.assertEquals(result.getId(), savedClient.getId());
        Assertions.assertEquals(result.getUsername(), savedClient.getUsername());
        Assertions.assertEquals(result.getPassword(), savedClient.getPassword());
        Assertions.assertEquals(result.getEmail(), savedClient.getEmail());
        Assertions.assertEquals(result.getPhone(), savedClient.getPhone());
        Assertions.assertEquals(result.getWalletBalance(), savedClient.getWalletBalance());
    }

    @Test
    void test_delete_shouldDeleteClient() {
        Client savedClient = clientRepository.save(client);

        List<Client> clientsBeforeDeleting = clientRepository.findAll();

        clientRepository.delete(savedClient);

        List<Client> clientsAfterDeleting = clientRepository.findAll();

        Assertions.assertNotNull(clientsBeforeDeleting);
        Assertions.assertNotNull(clientsAfterDeleting);

        Assertions.assertTrue(clientsBeforeDeleting.size() > clientsAfterDeleting.size());
    }

    @Test
    void test_update_shouldUpdateClient() {
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

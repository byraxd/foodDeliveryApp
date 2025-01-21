package com.example.foodDeliveryApp.client.service.impl;


import com.example.foodDeliveryApp.client.dto.ClientDto;
import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client firstClient;

    private Client secondClient;

    @BeforeEach
    public void setUp() {
        firstClient = Client
                .builder()
                .id(1L)
                .username("First Client")
                .password("Password of First Client")
                .email("first@example.com")
                .phone("+380131354524")
                .walletBalance(150.00)
                .build();

        secondClient = Client
                .builder()
                .id(2L)
                .username("Second Client")
                .password("Password of Second Client")
                .email("second@example.com")
                .phone("+380131314525")
                .walletBalance(290.00)
                .build();

    }

    @Test
    void test_getAll_shouldReturnListOfAllClients(){

        List<Client> allClient = List.of(firstClient, secondClient);

        Mockito.when(clientRepository.findAll()).thenReturn(allClient);

        List<Client> result = clientService.getAll();

        Assertions.assertEquals(allClient.size(), result.size());

        Mockito.verify(clientRepository, Mockito.times(1)).findAll();
    }

    @Test
    void test_getById_shouldReturnClientById_WhenClientIsFound(){
        Long id = 1L;

        Mockito.when(clientRepository.findById(id)).thenReturn(Optional.of(firstClient));

        Client result = clientService.getById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(firstClient, result);

        Mockito.verify(clientRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void test_save_shouldReturnSavedClient_WhenDtoIsValid(){
        ClientDto clientDto = ClientDto
                .builder()
                .username("First Client")
                .password("Password of First Client")
                .email("first@example.com")
                .phone("+380131354524")
                .walletBalance(150.00)
                .build();


        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(firstClient);

        Client result = clientService.save(clientDto);

        Assertions.assertNotNull(result);

        Assertions.assertEquals(firstClient.getUsername(), result.getUsername());
        Assertions.assertEquals(firstClient.getPassword(), result.getPassword());
        Assertions.assertEquals(firstClient.getEmail(), result.getEmail());
        Assertions.assertEquals(firstClient.getPhone(), result.getPhone());
        Assertions.assertEquals(firstClient.getWalletBalance(), result.getWalletBalance());

        Mockito.verify(clientRepository, Mockito.times(1)).save(Mockito.any(Client.class));

    }

    @Test
    void test_updateById_shouldReturnUpdatedClient_WhenDtoIsValid(){
        Long id = 1L;

        ClientDto clientDto = ClientDto
                .builder()
                .username("First Client")
                .password("Password of First Client")
                .email("first@example.com")
                .phone("+380131354524")
                .walletBalance(150.00)
                .build();

        Mockito.when(clientRepository.findById(id)).thenReturn(Optional.of(firstClient));
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(firstClient);

        Client result = clientService.updateById(id, clientDto);

        Assertions.assertNotNull(result);

        Assertions.assertEquals(firstClient.getUsername(), result.getUsername());
        Assertions.assertEquals(firstClient.getPassword(), result.getPassword());
        Assertions.assertEquals(firstClient.getEmail(), result.getEmail());
        Assertions.assertEquals(firstClient.getPhone(), result.getPhone());
        Assertions.assertEquals(firstClient.getWalletBalance(), result.getWalletBalance());

        Mockito.verify(clientRepository, Mockito.times(1)).findById(id);
        Mockito.verify(clientRepository, Mockito.times(1)).save(Mockito.any(Client.class));
    }

    @Test
    void test_deleteById_shouldReturnNothing_WhenClientIsExistsById(){
        Long id = 1L;

        Mockito.when(clientRepository.existsById(id)).thenReturn(true);

        clientService.deleteById(id);

        Mockito.verify(clientRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(id);

    }
}

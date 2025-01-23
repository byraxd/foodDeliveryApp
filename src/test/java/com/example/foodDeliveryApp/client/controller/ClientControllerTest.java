package com.example.foodDeliveryApp.client.controller;

import com.example.foodDeliveryApp.client.dto.ClientDto;
import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        this.client = Client
                .builder()
                .username("Client username")
                .password("Client password")
                .email("Client email")
                .build();
    }

    @Test
    void test_getAllClients() throws Exception {
        Mockito.when(clientService.getAll()).thenReturn(List.of(client));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(client.getUsername()))
                .andExpect(jsonPath("$[0].password").value(client.getPassword()))
                .andExpect(jsonPath("$[0].email").value(client.getEmail()));

        Mockito.verify(clientService, Mockito.times(1)).getAll();
    }

    @Test
    void test_getClientById() throws Exception {
        Long id = 1L;

        Mockito.when(clientService.getById(id)).thenReturn(client);

        mockMvc.perform(get("/api/clients/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(client.getUsername()))
                .andExpect(jsonPath("$.password").value(client.getPassword()))
                .andExpect(jsonPath("$.email").value(client.getEmail()));

        Mockito.verify(clientService, Mockito.times(1)).getById(id);
    }

    @Test
    void test_saveClient() throws Exception {
        String clientJson = """
                    {
                        "username": "Client username",
                        "password": "Client password",
                        "email": "Client email"
                    }
                """;

        Mockito.when(clientService.save(Mockito.any(ClientDto.class))).thenReturn(client);

        mockMvc.perform(post("/api/clients")
                .contentType("application/json")
                .content(clientJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(client.getUsername()))
                .andExpect(jsonPath("$.password").value(client.getPassword()))
                .andExpect(jsonPath("$.email").value(client.getEmail()));

        Mockito.verify(clientService, Mockito.times(1)).save(Mockito.any(ClientDto.class));
    }

    @Test
    void test_updateClient() throws Exception {
        Long id = 1L;

        String clientJson = """
                    {
                        "username": "Client username{updated}",
                        "password": "Client password",
                        "email": "Client email"
                    }
                """;

        client.setUsername("Client username{updated}");
        client.setPassword("Client password");
        client.setEmail("Client email");

        Mockito.when(clientService.updateById(Mockito.eq(id), Mockito.any(ClientDto.class))).thenReturn(client);

        mockMvc.perform(put("/api/clients/{id}", id)
                .contentType("application/json")
                .content(clientJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(client.getUsername()))
                .andExpect(jsonPath("$.password").value(client.getPassword()))
                .andExpect(jsonPath("$.email").value(client.getEmail()));

        Mockito.verify(clientService, Mockito.times(1)).updateById(Mockito.eq(id), Mockito.any(ClientDto.class));
    }

    @Test
    void test_deleteClient() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/clients/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(clientService, Mockito.times(1)).deleteById(id);
    }
}

package com.example.foodDeliveryApp.feedback.controller;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.feedback.dto.FeedbackDto;
import com.example.foodDeliveryApp.feedback.model.Feedback;
import com.example.foodDeliveryApp.feedback.service.FeedbackService;
import com.example.foodDeliveryApp.product.model.Product;
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

@WebMvcTest(FeedbackController.class)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    private Feedback feedback;

    @BeforeEach
    public void setUp() throws Exception {
        feedback = Feedback
                .builder()
                .text("text")
                .rating(10)
                .client(Client.builder().username("name of client").build())
                .product(Product.builder().name("name of Product").build())
                .build();
    }

    @Test
    void test_getAllFeedback() throws Exception {
        Mockito.when(feedbackService.getAll()).thenReturn(List.of(feedback));

        mockMvc.perform(get("/api/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value(feedback.getText()))
            .andExpect(jsonPath("$[0].rating").value(feedback.getRating()));

        Mockito.verify(feedbackService, Mockito.times(1)).getAll();
    }

    @Test
    void test_getFeedbackById() throws Exception {
        Mockito.when(feedbackService.getById(Mockito.anyLong())).thenReturn(feedback);

        mockMvc.perform(get("/api/feedbacks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(feedback.getText()))
                .andExpect(jsonPath("$.rating").value(feedback.getRating()));

        Mockito.verify(feedbackService, Mockito.times(1)).getById(Mockito.anyLong());
    }

    @Test
    void test_createFeedback() throws Exception {
        String jsonFeedback = """
                {
                "text": "text",
                "rating": "10",
                "client": {"username": "name of client"},
                "product": {"name": "name of Product"}
                }
                """;

        Mockito.when(feedbackService.save(Mockito.any(FeedbackDto.class))).thenReturn(feedback);

        mockMvc.perform(post("/api/feedbacks")
                .contentType("application/json")
                .content(jsonFeedback))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value(feedback.getText()))
                .andExpect(jsonPath("$.rating").value(feedback.getRating()));

        Mockito.verify(feedbackService, Mockito.times(1)).save(Mockito.any(FeedbackDto.class));
    }

    @Test
    void test_updateFeedback() throws Exception {
        Long id = 1L;
        String jsonFeedback = """
                {
                "text": "text {UPDATED}",
                "rating": "10",
                "client": {"username": "name of client"},
                "product": {"name": "name of Product"}
                }
                """;

        Mockito.when(feedbackService.updateById(Mockito.eq(id), Mockito.any(FeedbackDto.class))).thenReturn(feedback);

        mockMvc.perform(put("/api/feedbacks/{id}", id)
                .contentType("application/json")
                .content(jsonFeedback))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(feedback.getText()))
                .andExpect(jsonPath("$.rating").value(feedback.getRating()));

        Mockito.verify(feedbackService, Mockito.times(1)).updateById(Mockito.eq(id), Mockito.any(FeedbackDto.class));
    }

    @Test
    void test_deleteFeedback() throws Exception {
        Mockito.when(feedbackService.getById(Mockito.anyLong())).thenReturn(feedback);

        mockMvc.perform(delete("/api/feedbacks/{id}", 1L))
                .andExpect(status().isOk());
    }
}

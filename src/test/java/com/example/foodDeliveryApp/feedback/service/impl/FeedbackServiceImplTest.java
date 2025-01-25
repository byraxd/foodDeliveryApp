package com.example.foodDeliveryApp.feedback.service.impl;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.repository.ClientRepository;
import com.example.foodDeliveryApp.feedback.dto.FeedbackDto;
import com.example.foodDeliveryApp.feedback.model.Feedback;
import com.example.foodDeliveryApp.feedback.repository.FeedbackRepository;
import com.example.foodDeliveryApp.product.model.Product;
import com.example.foodDeliveryApp.product.repository.ProductRepository;
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
public class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    private Feedback firstFeedback;

    private Feedback secondFeedback;

    @BeforeEach
    public void setUp() {
        firstFeedback = Feedback
                .builder()
                .text("Text of the first feedback")
                .rating(10)
                .client(Client.builder().username("username").build())
                .product(Product.builder().name("product").build())
                .build();

        secondFeedback = Feedback
                .builder()
                .text("Text of the second feedback")
                .rating(5)
                .client(Client.builder().username("username").build())
                .product(Product.builder().name("product").build())
                .build();
    }

    @Test
    void test_getAll_shouldReturnAllFeedbacks() {
        List<Feedback> feedbacks = List.of(firstFeedback, secondFeedback);

        Mockito.when(feedbackRepository.findAll()).thenReturn(List.of(firstFeedback, secondFeedback));

        List<Feedback> result = feedbackService.getAll();

        Assertions.assertEquals(feedbacks, result);
        Assertions.assertEquals(firstFeedback, result.get(0));
        Assertions.assertEquals(secondFeedback, result.get(1));
        Assertions.assertEquals(result.size(), feedbacks.size());

        Mockito.verify(feedbackRepository, Mockito.times(1)).findAll();
    }

    @Test
    void test_getById_shouldReturnFeedback_ifFeedbackIsExist() {
        Mockito.when(feedbackRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstFeedback));

        Feedback result = feedbackService.getById(1L);

        Assertions.assertEquals(firstFeedback, result);

        Mockito.verify(feedbackRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void test_save_shouldSaveAndReturnFeedback_ifFeedbackDtoIsValid() {
        FeedbackDto feedbackDto = FeedbackDto
                .builder()
                .text("Text of the first feedback")
                .rating(10)
                .clientId(1L)
                .productId(1L)
                .build();

        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstFeedback.getClient()));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstFeedback.getProduct()));
        Mockito.when(feedbackRepository.save(Mockito.any(Feedback.class))).thenReturn(firstFeedback);

        Feedback result = feedbackService.save(feedbackDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(firstFeedback, result);

        Mockito.verify(feedbackRepository, Mockito.times(1)).save(Mockito.any(Feedback.class));
    }

    @Test
    void test_updateById_shouldUpdateAndReturnUpdatedFeedback_ifFeedbackDtoIsValid() {
        FeedbackDto feedbackDto = FeedbackDto
                .builder()
                .text("Text of the first feedback{UPDATED}")
                .rating(10)
                .clientId(1L)
                .productId(1L)
                .build();

        Mockito.when(feedbackRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstFeedback));
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstFeedback.getClient()));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstFeedback.getProduct()));
        Mockito.when(feedbackRepository.save(Mockito.any(Feedback.class))).thenReturn(firstFeedback);

        Feedback result = feedbackService.updateById(1L, feedbackDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(firstFeedback, result);

        Mockito.verify(feedbackRepository, Mockito.times(1)).save(Mockito.any(Feedback.class));
    }

    @Test
    void test_deleteById_shouldDeleteFeedback_ifFeedbackIsExist() {
        Long id = 1L;

        Mockito.when(feedbackRepository.existsById(id)).thenReturn(true);

        feedbackService.deleteById(id);

        Mockito.verify(feedbackRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(feedbackRepository, Mockito.times(1)).deleteById(id);
    }
}

package com.example.foodDeliveryApp.feedback.repository;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.repository.ClientRepository;
import com.example.foodDeliveryApp.feedback.model.Feedback;
import com.example.foodDeliveryApp.product.model.Product;
import com.example.foodDeliveryApp.product.model.ProductType;
import com.example.foodDeliveryApp.product.repository.ProductRepository;
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
public class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Feedback feedback;

    private Product product;

    private Client client;

    @BeforeEach
    public void setUp() {
        product = Product
                .builder()
                .name("Product Name")
                .type(ProductType.PIZZA)
                .description("Product Description")
                .price(10.00)
                .available(true)
                .imageUrl("Product Image")
                .build();

        productRepository.save(product);

        client = Client
                .builder()
                .username("Client username")
                .password("Client password")
                .email("email@test.com")
                .phone("123456789")
                .walletBalance(10.00)
                .build();

        clientRepository.save(client);

        feedback = Feedback
                .builder()
                .text("text of the feedback")
                .rating(10)
                .product(product)
                .client(client)
                .build();
    }

    @AfterEach
    public void tearDown() {
        feedbackRepository.deleteAll();
        productRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void givenFeedbackObject_whenFindAll_thenReturnListOfFeedback() {
        Feedback savedFeedback = feedbackRepository.save(feedback);

        List<Feedback> feedbackList = feedbackRepository.findAll();

        Assertions.assertNotNull(feedbackList);
        Assertions.assertEquals(feedbackList.get(0), savedFeedback);
    }

    @Test
    void givenFeedbackObject_whenFindById_thenReturnFeedback() {
        Feedback savedFeedback = feedbackRepository.save(feedback);

        Feedback foundFeedback = feedbackRepository.findById(savedFeedback.getId()).get();

        Assertions.assertNotNull(foundFeedback);
        Assertions.assertEquals(savedFeedback, foundFeedback);
    }

    @Test
    void giveFeedbackObject_whenSave_thenReturnSavedFeedback() {
        Feedback savedFeedback = feedbackRepository.save(feedback);

        Feedback foundSavedFeedback = feedbackRepository.findById(savedFeedback.getId()).get();

        Assertions.assertNotNull(foundSavedFeedback);
        Assertions.assertEquals(savedFeedback, foundSavedFeedback);
    }

    @Test
    void giveFeedBackObject_whenUpdate_thenReturnUpdatedFeedback() {
        Feedback savedFeedback = feedbackRepository.save(feedback);

        Feedback feedbackForUpdate = feedbackRepository.findById(savedFeedback.getId()).get();

        feedbackForUpdate.setText(feedbackForUpdate.getText() + "{Updated}");

        feedbackRepository.save(feedbackForUpdate);

        Feedback updatedFeedback = feedbackRepository.findById(savedFeedback.getId()).get();

        Assertions.assertNotNull(updatedFeedback);
        Assertions.assertEquals(feedbackForUpdate, updatedFeedback);
    }

    @Test
    void giveFeedbackObject_whenDelete_thenReturnDeletedFeedback() {
        Feedback savedFeedback = feedbackRepository.save(feedback);

        List<Feedback> feedbackListBeforeDeleting = feedbackRepository.findAll();

        feedbackRepository.deleteById(savedFeedback.getId());

        List<Feedback> feedbackListAfterDeleting = feedbackRepository.findAll();

        Assertions.assertTrue(feedbackListBeforeDeleting.size() > feedbackListAfterDeleting.size());
    }
}

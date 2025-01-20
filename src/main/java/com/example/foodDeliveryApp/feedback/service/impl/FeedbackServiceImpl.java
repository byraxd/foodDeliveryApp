package com.example.foodDeliveryApp.feedback.service.impl;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.repository.ClientRepository;
import com.example.foodDeliveryApp.exception.model.ClientNotFoundException;
import com.example.foodDeliveryApp.exception.model.FeedbackNotFoundException;
import com.example.foodDeliveryApp.exception.model.ProductNotFoundException;
import com.example.foodDeliveryApp.feedback.dto.FeedbackDto;
import com.example.foodDeliveryApp.feedback.model.Feedback;
import com.example.foodDeliveryApp.feedback.repository.FeedbackRepository;
import com.example.foodDeliveryApp.feedback.service.FeedbackService;
import com.example.foodDeliveryApp.product.model.Product;
import com.example.foodDeliveryApp.product.repository.ProductRepository;
import com.example.foodDeliveryApp.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Feedback> getAll() {
        log.info("Fetching all feedbacks");

        List<Feedback> feedbacks = feedbackRepository.findAll();
        log.info("Successfully fetched all feedbacks: {}", feedbacks);

        return feedbacks;
    }

    @Override
    public Feedback getById(Long id) {
        log.info("Fetching feedback by id: {}", id);

        ValidateUtils.validateId(id);

        Feedback feedback = findFeedbackById(id);
        log.info("Successfully fetched feedback by id: {}", feedback);

        return feedback;
    }

    @Override
    @Transactional
    public Feedback save(FeedbackDto feedbackDto) {
        log.info("Saving feedback: {}", feedbackDto);

        ValidateUtils.validateDto(feedbackDto);

        Feedback feedback = Feedback
                .builder()
                .rating(feedbackDto.rating())
                .text(feedbackDto.text())
                .client(findClientByClientId(feedbackDto.clientId()))
                .product(findProductByProductId(feedbackDto.productId()))
                .build();

        feedbackRepository.save(feedback);
        log.info("Successfully saved feedback: {}", feedback);

        return feedback;
    }

    @Override
    @Transactional
    public Feedback updateById(Long id, FeedbackDto feedbackDto) {
        log.info("Updating feedback: {}", feedbackDto);

        ValidateUtils.validateId(id);
        ValidateUtils.validateDto(feedbackDto);

        Feedback feedback = findFeedbackById(id);

        feedback.setRating(feedbackDto.rating());
        feedback.setText(feedbackDto.text());
        feedback.setClient(findClientByClientId(feedbackDto.clientId()));
        feedback.setProduct(findProductByProductId(feedbackDto.productId()));

        feedbackRepository.save(feedback);
        log.info("Successfully updated feedback: {}", feedback);

        return feedback;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting feedback: {}", id);

        ValidateUtils.validateId(id);

        if(!feedbackRepository.existsById(id)) {
            log.error("Feedback with id {} does not exist", id);
            throw new FeedbackNotFoundException("Feedback with id " + id + " does not exist");
        }

        feedbackRepository.deleteById(id);
        log.info("Successfully deleted feedback: {}", id);
    }

    private Feedback findFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElseThrow(() -> {
            log.error("Feedback not found by id: {}", id);
            return new FeedbackNotFoundException("Feedback with id " + id + " not found");
        });
    }

    private Client findClientByClientId(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> {
            log.error("Client not found by id: {}", clientId);
            return new ClientNotFoundException("Client with id " + clientId + " not found");
        });
    }

    private Product findProductByProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> {
            log.error("Product not found by id: {}", productId);
            return new ProductNotFoundException("Product with id " + productId + " not found");
        });
    }
}

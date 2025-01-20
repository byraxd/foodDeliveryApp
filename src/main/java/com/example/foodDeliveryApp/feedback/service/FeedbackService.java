package com.example.foodDeliveryApp.feedback.service;


import com.example.foodDeliveryApp.feedback.dto.FeedbackDto;
import com.example.foodDeliveryApp.feedback.model.Feedback;

import java.util.List;

public interface FeedbackService {

    List<Feedback> getAll();

    Feedback getById(Long id);

    Feedback save(FeedbackDto feedbackDto);

    Feedback updateById(Long id, FeedbackDto feedbackDto);

    void deleteById(Long id);
}

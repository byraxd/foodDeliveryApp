package org.example.feedback.service;

import org.example.feedback.dto.FeedbackDto;
import org.example.feedback.model.Feedback;

import java.util.List;

public interface FeedbackService {

    List<Feedback> getAll();

    Feedback getById(Long id);

    Feedback save(FeedbackDto feedbackDto);

    Feedback updateById(Long id, FeedbackDto feedbackDto);

    void deleteById(Long id);
}

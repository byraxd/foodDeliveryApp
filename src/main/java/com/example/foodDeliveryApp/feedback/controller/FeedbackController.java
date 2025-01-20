package com.example.foodDeliveryApp.feedback.controller;

import com.example.foodDeliveryApp.feedback.dto.FeedbackDto;
import com.example.foodDeliveryApp.feedback.model.Feedback;
import com.example.foodDeliveryApp.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<Feedback>> getFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody FeedbackDto feedbackDto) {
        return new ResponseEntity<>(feedbackService.save(feedbackDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody FeedbackDto feedbackDto) {
        return ResponseEntity.ok(feedbackService.updateById(id, feedbackDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteById(id);

        return ResponseEntity.ok("Feedback was successfully deleted");
    }
}

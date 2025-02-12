package com.example.foodDeliveryApp.product.model;

import com.example.foodDeliveryApp.feedback.model.Feedback;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private ProductType type;

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean available;

    @Column(nullable = false, unique = true)
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;
}

package com.example.foodDeliveryApp.feedback.model;

import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.product.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@id"
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}

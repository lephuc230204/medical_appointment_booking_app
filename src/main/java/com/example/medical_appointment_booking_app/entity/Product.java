package com.example.medical_appointment_booking_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "product")
public class Product {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long currentQuantity;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = true)
    private String description;

    @ManyToMany
    private List<User> user;

}

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
@Table( name = "cart")
public class Cart {

    @Id
    @GeneratedValue
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "product")
    private List<Product> product;
}

package com.example.medical_appointment_booking_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "orders")
public class Order {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate orderDate;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    private Double totalAmount;

    private String address;

    private String orderInfo;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        PENDING, CONFIRMED, SHIPPED, CANCELED
    }
    @Enumerated(EnumType.STRING)
    private Payment paymentMethod;
    public enum Payment {
        COD,ONLINE_PAYMENT,MOMO
    }
}

package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId(Long productId);
    @Query("SELECT p FROM Product p" +
            " WHERE p.productName = :productName" +
            " AND p.category.categoryName = :categoryName")
    Optional<Product> findByProductNameAndCategoryName(@Param("productName") String productName, @Param("categoryName") String categoryName);

}

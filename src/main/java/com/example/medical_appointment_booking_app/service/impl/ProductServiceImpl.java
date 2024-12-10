package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.Category;
import com.example.medical_appointment_booking_app.entity.Product;
import com.example.medical_appointment_booking_app.payload.request.Dto.ProductDto;
import com.example.medical_appointment_booking_app.payload.request.Form.ProductForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.payload.response.ResponseError;
import com.example.medical_appointment_booking_app.repository.ProductRepository;
import com.example.medical_appointment_booking_app.service.CategoryService;
import com.example.medical_appointment_booking_app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseData<ProductDto> create(ProductForm form) throws IOException {
        Category category = categoryService.checkOrCreateCategory(form.getCategoryName());

        String uploadedImagePath = null;
        if (form.getProductImage() != null && !form.getProductImage().isEmpty()) {
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "public/upload/product");

            // Tạo thư mục nếu chưa tồn tại
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Created product directory: " + uploadPath.toAbsolutePath());
            }

            String fileName = form.getProductImage().getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            form.getProductImage().transferTo(filePath.toFile());
            uploadedImagePath = "public/upload/product/" + fileName; // Đường dẫn để lưu vào cơ sở dữ liệu
        }else {
            log.warn("No image provided in the form.");
        }

        Product product = Product.builder()
                .productName(form.getProductName())
                .image(uploadedImagePath)
                .category(category)
                .quantity(form.getQuantity())
                .currentQuantity(form.getQuantity())
                .price(form.getPrice())
                .description(form.getDescription())
                .build();

        productRepository.save(product);
        return new ResponseData<>(200, "Create new product successfully", ProductDto.fromEntity(product));
    }

    @Override
    public ResponseData<Page<ProductDto>> getProduct(int page, int size){
        log.info("Get product list");
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductDto> data = products.map(ProductDto::fromEntity);
        return new ResponseData<>(200,"Get products successfully", data);
    }

    @Override
    public ResponseData<ProductDto> getProductById(Long productId) {
        log.info("Get product by id: {}", productId);
        Product product = productRepository.findByProductId(productId).orElse(null);
        if (product == null) {
            log.error("Product with id {} not found", productId);
            return new ResponseError<>(404, "Product not found");
        }

        return new ResponseData<>(200,"Get product successfully", ProductDto.fromEntity(product));
    }

    @Override
    public ResponseData<Void> deleteProduct(Long productId){
        log.info("Deleting product with id {}", productId);
        Product product = productRepository.findByProductId(productId).orElse(null);
        if (product == null) {
            log.error("Product with id {} not found", productId);
            return new ResponseError<>(404, "Product not found");
        }
        productRepository.delete(product);
        log.info("Product with id {} successfully deleted", productId);
        return new ResponseData<>(200,"Product successfully deleted");
    }



}
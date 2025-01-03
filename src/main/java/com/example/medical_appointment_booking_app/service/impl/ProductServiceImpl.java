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
        // Kiểm tra sản phẩm đã tồn tại trong cơ sở dữ liệu chưa
        Product existingProduct = productRepository.findByProductNameAndCategoryName(form.getProductName(), form.getCategoryName()).orElse(null);

        if (existingProduct != null) {
            // Nếu sản phẩm đã tồn tại, tăng số lượng
            existingProduct.setQuantity(existingProduct.getQuantity() + form.getQuantity());
            existingProduct.setCurrentQuantity(existingProduct.getCurrentQuantity() + form.getQuantity());
            productRepository.save(existingProduct);

            return new ResponseData<>(200, "Updated product quantity successfully", ProductDto.fromEntity(existingProduct));
        }

        // Nếu sản phẩm chưa tồn tại, tiếp tục tạo sản phẩm mới
        Category category = categoryService.checkOrCreateCategory(form.getCategoryName());
        String uploadedImagePath = null;

        if (form.getProductImage() != null && !form.getProductImage().isEmpty()) {
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "public/upload/product");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Created product directory: " + uploadPath.toAbsolutePath());
            }

            String fileName = form.getProductImage().getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            form.getProductImage().transferTo(filePath.toFile());
            uploadedImagePath = fileName; // Đường dẫn để lưu vào cơ sở dữ liệu
        } else {
            log.warn("No image provided in the form.");
        }

        Product product = Product.builder()
                .productName(form.getProductName())
                .image(uploadedImagePath)
                .category(category)
                .quantity(form.getQuantity())
                .currentQuantity(form.getQuantity())
                .price(form.getPrice())
                .weight(form.getWeight())
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

    public ResponseData<ProductDto> updateProduct(Long productId,ProductForm form) throws IOException {
        log.info("Updating product with id {}", productId);
        Product product = productRepository.findByProductId(productId).orElse(null);
        if (product == null) {
            log.error("Product with id {} not found", productId);
            return new ResponseError<>(404, "Product not found");
        }

        Category category = categoryService.checkOrCreateCategory(form.getCategoryName());

        String uploadedImagePath = null;
        if (form.getProductImage() != null && !form.getProductImage().isEmpty()) {
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "public/upload/product");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Created product directory: " + uploadPath.toAbsolutePath());
            }

            String fileName = form.getProductImage().getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            form.getProductImage().transferTo(filePath.toFile());
            uploadedImagePath = fileName; // Đường dẫn để lưu vào cơ sở dữ liệu
        }else {
            log.warn("No image provided in the form.");
            return new ResponseError<>(404, "No image provided in the form.");
        }
        product.setProductName(form.getProductName());
        product.setImage(uploadedImagePath);
        product.setCategory(category);
        product.setQuantity(form.getQuantity() + product.getQuantity());
        product.setCurrentQuantity(product.getCurrentQuantity() + form.getQuantity());
        product.setPrice(form.getPrice());
        product.setDescription(form.getDescription());

        productRepository.save(product);
        return new ResponseData<>(200,"Update product successfully", ProductDto.fromEntity(product));
    }


}
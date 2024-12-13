package com.example.medical_appointment_booking_app.config;

import com.example.medical_appointment_booking_app.entity.Order;
import com.example.medical_appointment_booking_app.entity.Role;
import com.example.medical_appointment_booking_app.entity.User;
import com.example.medical_appointment_booking_app.entity.User.Status;
import com.example.medical_appointment_booking_app.repository.OrderRepository;
import com.example.medical_appointment_booking_app.repository.RoleRepository;
import com.example.medical_appointment_booking_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private OrderRepository orderRepository;


    @PostConstruct
    public void init() {
        initRoles();
    }

    private void initRoles() {
        if (!roleRepository.findByName("ROLE_ADMIN").isPresent()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (!roleRepository.findByName("ROLE_USER").isPresent()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        initUsers();
    }

    private void initUsers() {
        // Lấy roles từ cơ sở dữ liệu
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        // Tạo người dùng admin
        User admin = User.builder()
                .email("admin@example.com")
                .password(passwordEncoder.encode("123456"))
                .role(roleAdmin)
                .username("admin")
                .dob(LocalDate.of(1990, 1, 1))
                .phoneNumber("1234567890")
                .status(Status.ACTIVE)
                .setCreatedDate(LocalDate.now())
                .build();

        userRepository.save(admin);

        // Tạo người dùng thường
        User user = User.builder()
                .email("user@example.com")
                .password(passwordEncoder.encode("123456"))
                .role(roleUser)
                .username("user")
                .dob(LocalDate.of(2000, 1, 1))
                .phoneNumber("0987654321")
                .status(Status.ACTIVE)
                .setCreatedDate(LocalDate.now())
                .build();

        userRepository.save(user);

        // Tạo một vài đơn hàng mẫu
        Order order1 = new Order();
        order1.setUser(user);
        order1.setOrderDate(LocalDate.now());
        order1.setStatus(Order.Status.PENDING);
        order1.setPaymentMethod(Order.Payment.MOMO);
        order1.setTotalAmount(100000L);
        order1.setOrderItems(Collections.emptyList()); // Bạn có thể thêm các OrderItem nếu cần

        Order order2 = new Order();
        order2.setUser(user);
        order2.setOrderDate(LocalDate.now().minusDays(1));
        order2.setStatus(Order.Status.CONFIRMED);
        order2.setPaymentMethod(Order.Payment.ONLINE_PAYMENT);
        order2.setTotalAmount(200000L);
        order2.setOrderItems(Collections.emptyList());

        // Lưu vào cơ sở dữ liệu
        orderRepository.save(order1);
        orderRepository.save(order2);
    }
}



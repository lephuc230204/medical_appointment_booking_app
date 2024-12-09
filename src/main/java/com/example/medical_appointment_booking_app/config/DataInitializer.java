//package com.example.medical_appointment_booking_app.config;
//
//import com.example.medical_appointment_booking_app.entity.Role;
//import com.example.medical_appointment_booking_app.entity.User;
//import com.example.medical_appointment_booking_app.entity.User.Status;
//import com.example.medical_appointment_booking_app.repository.RoleRepository;
//import com.example.medical_appointment_booking_app.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import jakarta.annotation.PostConstruct;
//import java.time.LocalDate;
//
//@Configuration
//public class DataInitializer {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void init() {
//        // Kiểm tra và lấy role "ROLE_ADMIN" và "ROLE_USER" từ cơ sở dữ liệu
//        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role ADMIN not found"));
//        Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role USER not found"));
//
//        // Tạo người dùng admin
//        User admin = User.builder()
//                .email("admin@example.com")
//                .password(passwordEncoder.encode("123456"))  // Mã hóa mật khẩu
//                .role(roleAdmin)
//                .username("admin")
//                .dob(LocalDate.of(1990, 1, 1))  // Ngày sinh
//                .phoneNumber("1234567890")
//                .status(Status.ACTIVE)  // Trạng thái là ACTIVE
//                .setCreatedDate(LocalDate.now())  // Ngày tạo
//                .build();
//
//        // Lưu người dùng admin vào cơ sở dữ liệu
//        userRepository.save(admin);
//
//        // Tạo người dùng thường
//        User user = User.builder()
//                .email("user@example.com")
//                .password(passwordEncoder.encode("123456"))
//                .role(roleUser)
//                .username("user")
//                .dob(LocalDate.of(2000, 1, 1))
//                .phoneNumber("0987654321")
//                .status(Status.ACTIVE)  // Trạng thái là ACTIVE
//                .setCreatedDate(LocalDate.now())
//                .build();
//
//        // Lưu người dùng thường vào cơ sở dữ liệu
//        userRepository.save(user);
//    }
//}

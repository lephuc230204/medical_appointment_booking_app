package com.example.medical_appointment_booking_app.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class MomoCallbackController {

    // API endpoint để nhận thông báo kết quả thanh toán từ MoMo
    @PostMapping("/ipn")
    public Map<String, Object> momoIPN(@RequestBody String body) {
        JSONObject json = new JSONObject(body);  // Chuyển đổi JSON String thành JSONObject
        System.out.println("MoMo IPN Response: " + json.toString());

        // Lấy các thông tin từ kết quả thanh toán gửi về
        String partnerCode = json.optString("partnerCode");
        String orderId = json.optString("orderId");
        String amount = json.optString("amount");
        String status = json.optString("status");
        String message = json.optString("message");
        String transactionId = json.optString("transactionId");

        // Kiểm tra trạng thái thanh toán
        if ("00".equals(status)) {
            // Trạng thái "00" có thể là thanh toán thành công, thực hiện cập nhật đơn hàng hoặc các hành động tiếp theo
            // Cập nhật trạng thái đơn hàng trong hệ thống (ví dụ: chuyển trạng thái đơn hàng thành "Đã thanh toán")
            // Code cập nhật dữ liệu trong DB hoặc các thao tác cần thiết
            System.out.println("Transaction successful. Order ID: " + orderId);
        } else {
            // Trạng thái khác có thể là thanh toán thất bại hoặc bị hủy
            // Cập nhật trạng thái đơn hàng trong hệ thống là thất bại hoặc hủy
            System.out.println("Transaction failed. Order ID: " + orderId + " with status: " + status);
        }

        // Phản hồi lại cho MoMo để xác nhận đã nhận thông báo IPN
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "IPN received successfully");

        // Trả về thông báo thành công hoặc thất bại tùy thuộc vào kết quả
        return response;
    }


    @GetMapping("/redirect")
    public String momoRedirect(@RequestParam Map<String, String> params) {
        return "Thanh toán thành công! Chi tiết: " + params.toString();
    }

}
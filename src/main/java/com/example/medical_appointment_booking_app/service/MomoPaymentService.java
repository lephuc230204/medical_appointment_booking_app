package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.config.HmacSHA256Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MomoPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(MomoPaymentService.class);

    @Value("${momo.partnerCode}")
    private String partnerCode;

    @Value("${momo.accessKey}")
    private String accessKey;

    @Value("${momo.secretKey}")
    private String secretKey;

    @Value("${momo.endpoint}")
    private String endpoint;

    public String createPaymentRequest(Long orderId, String orderInfo, long amount, String returnUrl, String notifyUrl) {
        logger.info("Creating payment request for Order ID: {}", orderId);

        // Kiểm tra notifyUrl không được để trống
        if (notifyUrl == null || notifyUrl.isBlank()) {
            logger.error("notifyUrl must not be null or blank.");
            throw new IllegalArgumentException("notifyUrl must not be null or blank.");
        }

        try {
            // Tạo chữ ký và body yêu cầu
            String requestId = String.valueOf(System.currentTimeMillis());
            String extraData = ""; // Không sử dụng dữ liệu bổ sung
            String rawHash = "partnerCode=" + partnerCode +
                    "&accessKey=" + accessKey +
                    "&requestId=" + requestId +
                    "&amount=" + amount +
                    "&orderId=" + orderId +
                    "&orderInfo=" + orderInfo +
                    "&returnUrl=" + returnUrl +
                    "&notifyUrl=" + notifyUrl +
                    "&extraData=" + extraData;

            String signature = HmacSHA256Utils.generateHmacSHA256(rawHash, secretKey);
            logger.debug("Generated signature: {}", signature);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("partnerCode", partnerCode);
            requestBody.put("accessKey", accessKey);
            requestBody.put("requestId", requestId);
            requestBody.put("amount", amount);
            requestBody.put("orderId", orderId);
            requestBody.put("orderInfo", orderInfo);
            requestBody.put("returnUrl", returnUrl);
            requestBody.put("notifyUrl", notifyUrl); // Đảm bảo notifyUrl không trống
            requestBody.put("extraData", extraData);
            requestBody.put("requestType", "captureWallet");
            requestBody.put("signature", signature);

            // Gửi request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, requestBody, Map.class);

            // Kiểm tra phản hồi
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("payUrl")) {
                return (String) responseBody.get("payUrl");
            } else {
                String resultCode = responseBody != null ? (String) responseBody.get("resultCode") : "null";
                String message = responseBody != null ? (String) responseBody.get("message") : "No message";
                logger.error("MoMo API returned error: resultCode={}, message={}", resultCode, message);
                return null;
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("HTTP Error: {}", e.getResponseBodyAsString(), e);
            return null;
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
            return null;
        }
    }


}



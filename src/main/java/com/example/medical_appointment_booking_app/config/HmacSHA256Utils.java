package com.example.medical_appointment_booking_app.config;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HmacSHA256Utils {
    public static String generateHMACSHA256(String data, String secretKey) {
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);
            byte[] bytes = sha256Hmac.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(bytes); // Base64 encode the result
        } catch (Exception e) {
            throw new RuntimeException("Error while generating HMAC-SHA256", e);
        }
    }
}

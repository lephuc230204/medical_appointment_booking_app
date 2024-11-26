package com.example.medical_appointment_booking_app.payload.response;

import java.io.Serializable;


public class ResponseData<T> implements Serializable {
    private final int status;
    private final String message;
    private T data;
    
    public ResponseData(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    public ResponseData(int status, String message) {
        this.status = status;
        this.message = message;
    }
    
    
}

package com.example.medical_appointment_booking_app.payload.response;

public class ResponseError<T> extends ResponseData<T> {
    public ResponseError(int status, String message) {
        super(status, message);
    }
}


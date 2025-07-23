package com.vegstore.user_service.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GlobalErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
    private String timestamp;

    // Constructor
    public GlobalErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Getters (or use Lombok @Data)
}

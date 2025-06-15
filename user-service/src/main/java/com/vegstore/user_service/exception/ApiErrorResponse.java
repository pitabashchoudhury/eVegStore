package com.vegstore.user_service.exception;


import lombok.Getter;

@Getter
public class ApiErrorResponse {
    private String message;
    private int statusCode;
    private String errorCode;

    public ApiErrorResponse() {}

    public ApiErrorResponse(String message, int statusCode, String errorCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    // Setters too (optional for response)
}

package com.vegstore.user_service.exception;

public class ApiErrorResponse {

    private String message;
    private String path;
    private int status;

    public ApiErrorResponse(String message, String path, int status) {
        this.message = message;
        this.path = path;
        this.status = status;
    }

    // ✅ Required getters for JSON serialization
    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public int getStatus() {
        return status;
    }
}

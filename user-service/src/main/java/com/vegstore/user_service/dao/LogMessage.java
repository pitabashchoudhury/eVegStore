package com.vegstore.user_service.dao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogMessage {
    private String className;
    private String methodName;
    private LocalDateTime timestamp;

    public LogMessage(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
        this.timestamp = LocalDateTime.now();
    }

    // Getters & Setters
}

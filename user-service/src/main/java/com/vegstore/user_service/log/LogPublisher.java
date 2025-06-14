package com.vegstore.user_service.log;

import com.vegstore.user_service.dao.LogMessage;

public interface LogPublisher {
    void publish(LogMessage message);
}

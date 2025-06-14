package com.vegstore.user_service.log;

import com.vegstore.user_service.dao.LogMessage;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchLogPublisher implements LogPublisher {

    @Override
    public void publish(LogMessage message) {
        // In real-world: send to Elasticsearch or Redis
        System.out.println("📦 Sending log to Elasticsearch: " + message.getClass() + "." + message.getMethodName() + " at " + message.getTimestamp());
    }
}

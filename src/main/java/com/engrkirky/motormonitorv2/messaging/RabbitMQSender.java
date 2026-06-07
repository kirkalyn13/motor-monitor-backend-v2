package com.engrkirky.motormonitorv2.messaging;

import com.engrkirky.motormonitorv2.config.RabbitMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for publishing messages to RabbitMQ.
 */
@Service
public class RabbitMQSender {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Sends a metrics message.
     *
     * @param messageObject message payload
     */
    public void sendMetricsMessage(Object messageObject) {
        try {
            String message = objectMapper.writeValueAsString(messageObject);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.METRICS_ROUTING_KEY,
                    message);
            log.info("Metrics message sent: " + message);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while sending metrics message: {}", e.getMessage());
        }
    }

    /**
     * Sends an alarm message.
     *
     * @param messageObject message payload
     */
    public void sendAlarmMessage(Object messageObject) {
        try {
            String message = objectMapper.writeValueAsString(messageObject);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ALARMS_ROUTING_KEY,
                    message);
            log.info("Alarm message sent: " + message);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while sending alarm message: {}", e.getMessage());
        }
    }
}


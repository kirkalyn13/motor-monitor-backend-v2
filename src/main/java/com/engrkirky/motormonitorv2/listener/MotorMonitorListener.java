package com.engrkirky.motormonitorv2.listener;

import com.engrkirky.motormonitorv2.config.RabbitMQConfig;
import com.engrkirky.motormonitorv2.service.MetricsService;
import com.engrkirky.motormonitorv2.service.MetricsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MotorMonitorListener {
    private static final Logger log = LoggerFactory.getLogger(MotorMonitorListener.class);
    private final MetricsService metricsService;

    @Autowired
    public MotorMonitorListener(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @RabbitListener(queues = RabbitMQConfig.METRICS_QUEUE_NAME)
    public void onMetricsMessage(String message) {
        log.info("Received Metrics: {}", message);
        // handle message here
    }

    @RabbitListener(queues = RabbitMQConfig.ALARMS_QUEUE_NAME)
    public void onAlarmMessage(String message) {
        log.info("Received Alarm: {}", message);
        // handle message here
    }
}

package com.engrkirky.motormonitorv2.listener;

import com.engrkirky.motormonitorv2.config.RabbitMQConfig;
import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.service.AlarmService;
import com.engrkirky.motormonitorv2.service.MetricsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MotorMonitorListener {
    private static final Logger log = LoggerFactory.getLogger(MotorMonitorListener.class);
    private final MetricsService metricsService;
    private final AlarmService alarmService;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);;

    @Autowired
    public MotorMonitorListener(MetricsService metricsService, AlarmService alarmService) {
        this.metricsService = metricsService;
        this.alarmService = alarmService;
    }

    @RabbitListener(queues = RabbitMQConfig.METRICS_QUEUE_NAME)
    public void onMetricsMessage(String message) {
        try {
            MetricsDTO metricsDTO = objectMapper.readValue(message, MetricsDTO.class);
            String motorID =metricsDTO.motorID();
            metricsService.addMetrics(motorID, metricsDTO);
            log.info("Metrics saved for motor: {}", motorID);
        } catch (JsonProcessingException e) {
            log.error("Error parsing metrics message: {}", e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.ALARMS_QUEUE_NAME)
    public void onAlarmMessage(String message) {
        try {
            List<AlarmDTO> alarmsDTO = objectMapper.readValue(
                    message,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, AlarmDTO.class)
            );
            String motorID = alarmsDTO.get(0).motorID();
            alarmService.addAlarms(motorID, alarmsDTO);
            log.info("Alarms saved for motor: {}", motorID);
        } catch (JsonProcessingException e) {
            log.error("Error parsing alarm message: {}", e.getMessage());
        }
    }
}

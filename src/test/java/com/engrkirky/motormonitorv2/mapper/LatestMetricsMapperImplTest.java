package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.LatestMetricsDTO;
import com.engrkirky.motormonitorv2.dto.MetricStatusDTO;
import com.engrkirky.motormonitorv2.model.Metrics;
import com.engrkirky.motormonitorv2.util.Severities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LatestMetricsMapperImplTest {
    @Autowired
    private LatestMetricsMapperImpl underTest;
    private static final LocalDateTime now = LocalDateTime.now();
    private static final String motorId = "1137";
    private static final LatestMetricsDTO dto = new LatestMetricsDTO(
            now,
            motorId,
            new MetricStatusDTO(228, Severities.NORMAL),
            new MetricStatusDTO(229, Severities.NORMAL),
            new MetricStatusDTO(230, Severities.NORMAL),
            new MetricStatusDTO(1.58, Severities.NORMAL),
            new MetricStatusDTO(1.60, Severities.NORMAL),
            new MetricStatusDTO(1.62, Severities.NORMAL),
            new MetricStatusDTO(27, Severities.NORMAL)
    );

    private static final Metrics metrics = Metrics.builder()
            .timestamp(now)
            .motorID(motorId)
            .line1Voltage(228)
            .line2Voltage(229)
            .line3Voltage(230)
            .line1Current(1.58)
            .line2Current(1.60)
            .line3Current(1.62)
            .temperature(27)
            .build();

    @BeforeEach
    void setUp() {
        this.underTest = new LatestMetricsMapperImpl();
    }

    @Test
    void shouldConvertToLatestMetricsDTO() {
        LatestMetricsDTO result = underTest.convertToLatestMetricsDTO(metrics, 230, 1.6, 50);
        assertEquals(result, dto);
    }
}

package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.model.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetricsMapperImplTest {
    @Autowired
    private MetricsMapperImpl underTest;
    private static final LocalDateTime now = LocalDateTime.now();
    private static final String motorId = "1137";
    private static final MetricsDTO dto = new MetricsDTO(
            1L,
            now,
            motorId,
            228,
            229,
            230,
            1.58,
            1.60,
            1.62,
            28
    );

    private static final Metrics entity = Metrics.builder()
            .id(1L)
            .timestamp(now)
            .motorID(motorId)
            .line1Voltage(228)
            .line2Voltage(229)
            .line3Voltage(230)
            .line1Current(1.58)
            .line2Current(1.60)
            .line3Current(1.62)
            .temperature(28)
            .build();
    @BeforeEach
    void setUp() {
        this.underTest = new MetricsMapperImpl();
    }

    @Test
    void shouldConvertToDTO() {
        MetricsDTO result = underTest.convertToDTO(entity);
        assertEquals(result, dto);
    }

    @Test
    void shouldConvertToEntity() {
        Metrics result = underTest.convertToEntity(dto);
        assertEquals(result, entity);
    }
}

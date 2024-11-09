package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.model.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetricsMapperImplTest {
    private MetricsMapperImpl underTest;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        this.now = LocalDateTime.now();
        this.underTest = new MetricsMapperImpl();
    }

    MetricsDTO dto = new MetricsDTO(
        1L,
            now,
            "1137",
            228,
            229,
            230,
            1.58,
            1.60,
            1.62,
            28
    );

    Metrics entity = Metrics.builder()
            .id(1L)
            .timestamp(now)
            .motorID("1137")
            .line1Voltage(228)
            .line2Voltage(229)
            .line3Voltage(230)
            .line1Current(1.58)
            .line2Current(1.60)
            .line3Current(1.62)
            .temperature(28)
            .build();

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

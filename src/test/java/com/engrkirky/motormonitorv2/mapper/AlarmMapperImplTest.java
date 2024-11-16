package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.model.Alarm;
import com.engrkirky.motormonitorv2.util.Severities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlarmMapperImplTest {
    private AlarmMapperImpl underTest;
    private final LocalDateTime now = LocalDateTime.now();
    private final String motorId = "1137";

    @BeforeEach
    void setUp() {
        underTest = new AlarmMapperImpl();
    }

    AlarmDTO dto = new AlarmDTO(now, motorId, "Motor Alarm", Severities.CRITICAL);
    Alarm entity = Alarm.builder()
            .id(1L)
            .timestamp(now)
            .motorID(motorId)
            .alarm("Motor Alarm")
            .severity(Severities.CRITICAL)
            .build();

    @Test
    void shouldConvertToDTO() {
        AlarmDTO result = underTest.convertToDTO(entity);
        assertEquals(result, dto);
    }

    @Test
    void shouldConvertToEntity() {
        Alarm result = underTest.convertToEntity(dto);
        result.setId(1L); // mock auto generate ID
        assertEquals(result, entity);
    }
}

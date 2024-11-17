package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.mapper.AlarmMapper;
import com.engrkirky.motormonitorv2.model.Alarm;
import com.engrkirky.motormonitorv2.repository.AlarmRepository;
import com.engrkirky.motormonitorv2.util.Severities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlarmServiceImplTest {
    @Mock private AlarmRepository alarmRepository;
    @Mock private AlarmMapper alarmMapper;
    @InjectMocks private AlarmServiceImpl underTest;
    private static final LocalDateTime now = LocalDateTime.now();
    private static final String motorId = "1137";
    private static final MetricsDTO metricsDTO = new MetricsDTO(
            1L,
            now,
            motorId,
            230.0,
            230.5,
            229.5,
            10.0,
            10.5,
            10.3,
            80.0 );
    @BeforeEach
    void setUp() {
        underTest = new AlarmServiceImpl(alarmRepository, alarmMapper);
    }

    @Test
    void shouldReturnAlarmHistoryByMotorID() {
        int limit = 15;
        AlarmDTO alarmDTO = new AlarmDTO(now, motorId, "Phase 1 Over Voltage", Severities.CRITICAL);
        List<Alarm> alarms = List.of(Alarm.builder().id(1L).timestamp(now).motorID(motorId).alarm("Phase 1 Over Voltage").severity(Severities.CRITICAL).build());
        List<AlarmDTO> alarmDTOs = List.of(alarmDTO);

        when(alarmRepository.findAlarmHistoryByMotorID(anyString(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(alarms);
        when(alarmMapper.convertToDTO(any(Alarm.class))).thenReturn(alarmDTO);

        List<AlarmDTO> result = underTest.getAlarmsHistoryByMotorID(motorId, limit);

        assertEquals(alarmDTOs.size(), result.size());
        assertEquals(alarmDTOs.get(0), result.get(0));
    }

    @Test
    void shouldAnalyzeMetricsAndReturnAlarms() {
        double ratedVoltage = 220.0;
        double ratedCurrent = 10.0;
        double maxTemperature = 50.0;

        AlarmDTO expectedAlarm = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Motor Overheating", Severities.CRITICAL);

        List<AlarmDTO> result = underTest.analyzeMetrics(metricsDTO, ratedVoltage, ratedCurrent, maxTemperature);

        assertEquals(result.get(0).motorID(), expectedAlarm.motorID());
        assertEquals(result.get(0).alarm(), expectedAlarm.alarm());
        assertEquals(result.get(0).severity(), expectedAlarm.severity());
    }

    @Test
    void shouldAddAlarmsAndReturnId() {
        List<AlarmDTO> alarmDTOs = List.of(new AlarmDTO(now, motorId, "Phase 1 Over Voltage", Severities.CRITICAL));
        List<Alarm> alarms = List.of(new Alarm());

        when(alarmMapper.convertToEntity(any(AlarmDTO.class))).thenReturn(alarms.get(0));

        String result = underTest.addAlarms(motorId, alarmDTOs);

        verify(alarmRepository).saveAll(alarms);
        assertEquals(result, motorId);
    }
}

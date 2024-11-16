package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.mapper.AlarmMapper;
import com.engrkirky.motormonitorv2.model.Alarm;
import com.engrkirky.motormonitorv2.repository.AlarmRepository;
import com.engrkirky.motormonitorv2.util.AlarmUtil;
import com.engrkirky.motormonitorv2.util.Severities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AlarmUtil.class)
@ExtendWith(MockitoExtension.class)
public class AlarmServiceImplTest {
    @Mock
    private AlarmRepository alarmRepository;

    @Mock
    private AlarmMapper alarmMapper;

    @InjectMocks
    private AlarmServiceImpl underTest;

    private LocalDateTime now;
    private final String motorId = "1137";
    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
        underTest = new AlarmServiceImpl(alarmRepository, alarmMapper);
        this.now = LocalDateTime.now();
    }

//    @Test
    void shouldReturnAlarmHistoryByMotorID() {
        int limit = 15;
        LocalDateTime[] timeRange = {LocalDateTime.now().minusMinutes(limit), LocalDateTime.now()};
        List<Alarm> alarms = List.of(Alarm.builder().id(1L).timestamp(now).motorID(motorId).alarm("Phase 1 Over Voltage").severity(Severities.CRITICAL).build());
        List<AlarmDTO> alarmDTOs = List.of(new AlarmDTO(now, motorId, "Phase 1 Over Voltage", Severities.CRITICAL));

        when(alarmRepository.findAlarmHistoryByMotorID(motorId, timeRange[0], timeRange[1])).thenReturn(alarms);
        when(alarmMapper.convertToDTO(any(Alarm.class))).thenReturn(alarmDTOs.get(0));

        List<AlarmDTO> result = underTest.getAlarmsHistoryByMotorID(motorId, limit);

        verify(alarmRepository).findAlarmHistoryByMotorID(motorId, timeRange[0], timeRange[1]);
        assertEquals(alarmDTOs.size(), result.size());
        assertEquals(alarmDTOs.get(0), result.get(0));
    }

    @Test
    void shouldAnalyzeMetricsAndReturnAlarms() {
        MetricsDTO metricsDTO = new MetricsDTO(1L, now, motorId, 230.0, 230.5, 229.5, 10.0, 10.5, 10.3, 80.0);
        double ratedVoltage = 220.0;
        double ratedCurrent = 10.0;
        double maxTemperature = 75.0;

        AlarmDTO overVoltageAlarm = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 1 Over Voltage", Severities.CRITICAL);
        when(AlarmUtil.checkOverVoltage(metricsDTO.line1Voltage(), ratedVoltage)).thenReturn(Severities.CRITICAL);

        List<AlarmDTO> result = underTest.analyzeMetrics(metricsDTO, ratedVoltage, ratedCurrent, maxTemperature);

        assertThat(result).hasSize(1).contains(overVoltageAlarm);
    }

//    @Test
    void shouldAddAlarmsAndReturnId() {
        List<AlarmDTO> alarmDTOs = List.of(new AlarmDTO(now, motorId, "Phase 1 Over Voltage", Severities.CRITICAL));
        List<Alarm> alarms = List.of(new Alarm());

        when(alarmMapper.convertToEntity(any(AlarmDTO.class))).thenReturn(alarms.get(0));

        String result = underTest.addAlarms(motorId, alarmDTOs);

        assertThat(result).isEqualTo(motorId);
        verify(alarmRepository).saveAll(alarms);
    }
}

package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.*;
import com.engrkirky.motormonitorv2.mapper.LatestMetrcisMapper;
import com.engrkirky.motormonitorv2.mapper.MetricsMapper;
import com.engrkirky.motormonitorv2.model.Metrics;
import com.engrkirky.motormonitorv2.repository.MetricsRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MetricsServiceImplTest {
    @Mock private MetricsRepository metricsRepository;
    @Mock private MetricsMapper metricsMapper;
    @Mock private LatestMetrcisMapper latestMetrcisMapper;
    @Mock private AlarmService alarmService;
    @InjectMocks private MetricsServiceImpl underTest;
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
    private static final Metrics metrics = Metrics.builder()
            .id(1L)
            .timestamp(now)
            .motorID(motorId)
            .line1Voltage(230.0)
            .line2Voltage(230.5)
            .line3Voltage(229.5)
            .line1Current(10.0)
            .line2Current(10.3)
            .line3Current(10.3)
            .temperature(80.0)
            .build();
    private static final LatestMetricsDTO latestMetricsDTO = new LatestMetricsDTO(
            now,
            motorId,
            new MetricStatusDTO(230.0, Severities.NORMAL),
            new MetricStatusDTO(230.5, Severities.NORMAL),
            new MetricStatusDTO(229.5, Severities.NORMAL),
            new MetricStatusDTO(10.0, Severities.NORMAL),
            new MetricStatusDTO(10.5, Severities.NORMAL),
            new MetricStatusDTO(10.3, Severities.NORMAL),
            new MetricStatusDTO(80.0, Severities.NORMAL)
    );

    private static final AlarmDTO alarmDTO = new AlarmDTO(
            now,
            motorId,
            "Phase 1 Over Voltage",
            Severities.CRITICAL );
    private static final double ratedVoltage = 220.0;
    private static final double ratedCurrent = 10.0;
    private static final double maxTemperature = 75.0;
    private static final int limit = 60;

    @BeforeEach
    void setUp() {
        underTest = new MetricsServiceImpl(metricsRepository, metricsMapper, latestMetrcisMapper, alarmService);
    }

    @Test
    void shouldGetLatestMetrics() {
        when(metricsRepository.findLatestMetrics(motorId)).thenReturn(metrics);
        when(latestMetrcisMapper.convertToLatestMetricsDTO(metrics, ratedVoltage, ratedCurrent, maxTemperature)).thenReturn(latestMetricsDTO);

        LatestMetricsDTO result = underTest.getLatestMetrics(motorId, ratedVoltage, ratedCurrent, maxTemperature);

        assertEquals(result, latestMetricsDTO);
    }

    @Test
    void shouldGetVoltageTrend() {
        List<VoltageDTO> expectedTrend = List.of(new VoltageDTO(now, 230.0, 230.5, 229.5));

        lenient().when(metricsRepository.findVoltageTrend(anyString(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(expectedTrend);

        List<VoltageDTO> result = underTest.getVoltageTrend(motorId, limit);

        assertEquals(result.size(), expectedTrend.size());
        assertEquals(result.get(0), expectedTrend.get(0));
    }

    @Test
    void shouldGetCurrentTrend() {
        List<CurrentDTO> expectedTrend = List.of(new CurrentDTO(now, 10.0, 10.5, 10.3));

        lenient().when(metricsRepository.findCurrentTrend(anyString(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(expectedTrend);

        List<CurrentDTO> result = underTest.getCurrentTrend(motorId, limit);

        assertEquals(result.size(), expectedTrend.size());
        assertEquals(result.get(0), expectedTrend.get(0));
    }

    @Test
    void shouldGetTemperatureTrend() {
        List<TemperatureDTO> expectedTrend = List.of(new TemperatureDTO(now, 80.0));

        lenient().when(metricsRepository.findTemperatureTrend(anyString(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(expectedTrend);

        List<TemperatureDTO> result = underTest.getTemperatureTrend(motorId, limit);

        assertEquals(result.size(), expectedTrend.size());
        assertEquals(result.get(0), expectedTrend.get(0));
    }

    @Test
    void shouldAddMetrics() {
        List<AlarmDTO> alarms = List.of(alarmDTO);

        when(metricsMapper.convertToEntity(metricsDTO)).thenReturn(metrics);
        when(alarmService.analyzeMetrics(metricsDTO, ratedVoltage, ratedCurrent, maxTemperature)).thenReturn(alarms);

        String result = underTest.addMetrics(motorId, metricsDTO, ratedVoltage, ratedCurrent, maxTemperature);

        verify(metricsRepository).save(metrics);
        verify(alarmService).addAlarms(motorId, alarms);
        assertEquals(result, motorId);
    }

    @Test
    void shouldGetMetricsLogs() {
        List<Metrics> entities = List.of(new Metrics());
        List<MetricsDTO> expectedLogs = List.of(metricsDTO);

        lenient().when(metricsRepository.findMetricsLogs(anyString(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(entities);
        when(metricsMapper.convertToDTO(any(Metrics.class))).thenReturn(expectedLogs.get(0));

        List<MetricsDTO> result = underTest.getMetricsLogs(motorId, limit);

        assertEquals(result, expectedLogs);
    }

    @Test
    void shouldGetMetricsSummary() {
        MetricsSummaryDTO expectedSummary = new MetricsSummaryDTO(new int[] {7, 0, 0});

        when(metricsRepository.findLatestMetrics(motorId)).thenReturn(metrics);
        when(latestMetrcisMapper.convertToLatestMetricsDTO(metrics, ratedVoltage, ratedCurrent, maxTemperature)).thenReturn(latestMetricsDTO);

        MetricsSummaryDTO result = underTest.getMetricsSummary(motorId, ratedVoltage, ratedCurrent, maxTemperature);

        for (var i = 0; i <= 2; i++) {
            assertEquals(result.summary()[i], expectedSummary.summary()[i]);
        }
    }

    @Test
    void shouldGetAlarms() {
        List<AlarmDTO> expectedAlarms = List.of(alarmDTO);

        when(metricsRepository.findLatestMetrics(motorId)).thenReturn(metrics);
        when(metricsMapper.convertToDTO(metrics)).thenReturn(metricsDTO);
        when(alarmService.analyzeMetrics(metricsDTO, ratedVoltage, ratedCurrent, maxTemperature)).thenReturn(expectedAlarms);

        List<AlarmDTO> result = underTest.getAlarms(motorId, ratedVoltage, ratedCurrent, maxTemperature);

        assertEquals(result, expectedAlarms);
    }
}


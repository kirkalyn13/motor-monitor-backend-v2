package com.engrkirky.motormonitorv2.repository;

import com.engrkirky.motormonitorv2.dto.CurrentDTO;
import com.engrkirky.motormonitorv2.dto.TemperatureDTO;
import com.engrkirky.motormonitorv2.dto.VoltageDTO;
import com.engrkirky.motormonitorv2.model.Metrics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class MetricsRepositoryTest {
    @Autowired
    private MetricsRepository underTest;

    private final LocalDateTime now = LocalDateTime.now();;
    private static final String MOTOR_ID = "1137";

    @BeforeEach
    public void setUp() {
        underTest.save(new Metrics(1L, now.minusDays(1), MOTOR_ID, 220.0, 220.5, 219.8, 10.0, 10.5, 10.3, 75.0));
        underTest.save(new Metrics(2L, now.minusHours(12), MOTOR_ID, 221.0, 220.7, 220.1, 10.1, 10.6, 10.4, 76.0));
        underTest.save(new Metrics(3L, now.minusHours(1), MOTOR_ID, 222.0, 220.9, 220.3, 10.2, 10.7, 10.5, 77.0));
        underTest.save(new Metrics(4L, now.minusHours(1), "12345", 223.0, 221.0, 220.6, 10.3, 10.8, 10.6, 78.0));
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void shouldFindLatestMetrics() {
        Metrics latestMetrics = underTest.findLatestMetrics(MOTOR_ID);
        assertThat(latestMetrics).isNotNull();
        assertThat(latestMetrics.getMotorID()).isEqualTo(MOTOR_ID);
        assertThat(latestMetrics.getTimestamp()).isAfter(now.minusHours(1));
    }

    @Test
    public void shouldFindVoltageTrend() {
        LocalDateTime start = now.minusDays(2);
        LocalDateTime end = now.plusDays(1);

        List<VoltageDTO> voltageTrend = underTest.findVoltageTrend(MOTOR_ID, start, end);
        assertThat(voltageTrend).hasSize(3);
        assertThat(voltageTrend.get(0).line1Voltage()).isNotNull();
        assertThat(voltageTrend.get(1).line1Voltage()).isNotNull();
        assertThat(voltageTrend.get(2).line1Voltage()).isNotNull();
        assertThat(voltageTrend.get(0).line2Voltage()).isNotNull();
        assertThat(voltageTrend.get(1).line2Voltage()).isNotNull();
        assertThat(voltageTrend.get(2).line2Voltage()).isNotNull();
        assertThat(voltageTrend.get(0).line3Voltage()).isNotNull();
        assertThat(voltageTrend.get(1).line3Voltage()).isNotNull();
        assertThat(voltageTrend.get(2).line3Voltage()).isNotNull();
        assertThat(voltageTrend.get(0).timestamp()).isAfter(now.minusHours(1));
        assertThat(voltageTrend.get(1).timestamp()).isAfter(now.minusHours(12));
        assertThat(voltageTrend.get(2).timestamp()).isAfter(now.minusDays(1));
    }

    @Test
    public void shouldFindCurrentTrend() {
        LocalDateTime start = now.minusDays(2);
        LocalDateTime end = now.plusDays(1);

        List<CurrentDTO> currentTrend = underTest.findCurrentTrend(MOTOR_ID, start, end);
        assertThat(currentTrend).hasSize(3);
        assertThat(currentTrend.get(0).line1Current()).isNotNull();
        assertThat(currentTrend.get(1).line1Current()).isNotNull();
        assertThat(currentTrend.get(2).line1Current()).isNotNull();
        assertThat(currentTrend.get(0).line2Current()).isNotNull();
        assertThat(currentTrend.get(1).line2Current()).isNotNull();
        assertThat(currentTrend.get(2).line2Current()).isNotNull();
        assertThat(currentTrend.get(0).line3Current()).isNotNull();
        assertThat(currentTrend.get(1).line3Current()).isNotNull();
        assertThat(currentTrend.get(2).line3Current()).isNotNull();
        assertThat(currentTrend.get(0).timestamp()).isAfter(now.minusHours(1));
        assertThat(currentTrend.get(1).timestamp()).isAfter(now.minusHours(12));
        assertThat(currentTrend.get(2).timestamp()).isAfter(now.minusDays(1));
    }

    @Test
    public void shouldFindTemperatureTrend() {
        LocalDateTime start = now.minusDays(2);
        LocalDateTime end = now.plusDays(1);

        List<TemperatureDTO> temperatureTrend = underTest.findTemperatureTrend(MOTOR_ID, start, end);
        assertThat(temperatureTrend).hasSize(3);
        assertThat(temperatureTrend.get(0).temperature()).isNotNull();
        assertThat(temperatureTrend.get(1).temperature()).isNotNull();
        assertThat(temperatureTrend.get(2).temperature()).isNotNull();
        assertThat(temperatureTrend.get(0).timestamp()).isAfter(now.minusHours(1));
        assertThat(temperatureTrend.get(1).timestamp()).isAfter(now.minusHours(12));
        assertThat(temperatureTrend.get(2).timestamp()).isAfter(now.minusDays(1));
    }

    @Test
    public void shouldFindMetricsLogs() {
        LocalDateTime start = now.minusDays(2);
        LocalDateTime end = now.plusDays(1);

        List<Metrics> metricsLogs = underTest.findMetricsLogs(MOTOR_ID, start, end);
        assertThat(metricsLogs).hasSize(3);
        assertThat(metricsLogs.get(0).getTimestamp()).isAfter(now.minusHours(1));
        assertThat(metricsLogs.get(1).getTimestamp()).isAfter(now.minusHours(12));
        assertThat(metricsLogs.get(2).getTimestamp()).isAfter(now.minusDays(1));
    }
}

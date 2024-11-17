package com.engrkirky.motormonitorv2.repository;

import com.engrkirky.motormonitorv2.model.Alarm;
import com.engrkirky.motormonitorv2.util.Severities;
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
public class AlarmRepositoryTest {
    @Autowired
    private AlarmRepository underTest;
    private static final LocalDateTime now = LocalDateTime.now();
    private static final String motorId = "1137";

    @BeforeEach
    void setUp() {
        underTest.save(new Alarm(1L, now, motorId, "Phase 1 Over Voltage", Severities.CRITICAL)); // within range
        underTest.save(new Alarm(2L, now, motorId, "", Severities.CRITICAL)); // within range
        underTest.save(new Alarm(3L, now, "12345", "No Data for Phase 1 Voltage", Severities.CRITICAL)); // different motor ID
        underTest.save(new Alarm(4L, now, motorId, "No Data for Phase 1 Voltage", Severities.CRITICAL)); // out of range
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void shouldFindAlarmHistoryByMotorID() {
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        List<Alarm> alarms = underTest.findAlarmHistoryByMotorID(motorId, start, end);

        assertThat(alarms).hasSize(3);
        assertThat(alarms).allMatch(alarm -> alarm.getMotorID().equals(motorId));
        assertThat(alarms).allMatch(alarm ->
                !alarm.getTimestamp().isBefore(start) && !alarm.getTimestamp().isAfter(end)
        );
    }
}

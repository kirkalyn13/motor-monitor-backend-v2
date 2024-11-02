package com.engrkirky.motormonitorv2.util;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlarmUtilTest {
    @Test
    void shouldCheckOverVoltage() {
        double threshold = 100;

        // Test CRITICAL
        assertEquals(Severities.CRITICAL, AlarmUtil.checkOverVoltage(115, threshold));
        assertEquals(Severities.CRITICAL, AlarmUtil.checkOverVoltage(120, threshold));

        // Test WARNING
        assertEquals(Severities.WARNING, AlarmUtil.checkOverVoltage(111, threshold));
        assertEquals(Severities.WARNING, AlarmUtil.checkOverVoltage(112, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, AlarmUtil.checkOverVoltage(105, threshold));
        assertEquals(Severities.NORMAL, AlarmUtil.checkOverVoltage(95, threshold));
    }

    @Test
    void shouldCheckUnderVoltage() {
        double threshold = 100;

        // Test CRITICAL
        assertEquals(Severities.CRITICAL, AlarmUtil.checkUnderVoltage(85, threshold));
        assertEquals(Severities.CRITICAL, AlarmUtil.checkUnderVoltage(15, threshold));

        // Test WARNING
        assertEquals(Severities.WARNING, AlarmUtil.checkUnderVoltage(90, threshold));
        assertEquals(Severities.WARNING, AlarmUtil.checkUnderVoltage(89, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, AlarmUtil.checkUnderVoltage(95, threshold));
        assertEquals(Severities.NORMAL, AlarmUtil.checkUnderVoltage(105, threshold));
    }

    @Test
    void shouldCheckNoPower() {
        double threshold = 100;

        // Test CRITICAL
        assertEquals(Severities.CRITICAL, AlarmUtil.checkNoPower(0, threshold));
        assertEquals(Severities.CRITICAL, AlarmUtil.checkNoPower(5, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, AlarmUtil.checkNoPower(15, threshold));
    }

    @Test
    void shouldCheckShortCircuit() {
        double threshold = 100;

        // Test CRITICAL
        assertEquals(Severities.CRITICAL, AlarmUtil.checkShortCircuit(400, threshold));
        assertEquals(Severities.CRITICAL, AlarmUtil.checkShortCircuit(450, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, AlarmUtil.checkShortCircuit(300, threshold));
        assertEquals(Severities.NORMAL, AlarmUtil.checkShortCircuit(95, threshold));
    }

    @Test
    void shouldCheckCurrentOverload() {
        double threshold = 100;

        // Test CRITICAL
        assertEquals(Severities.CRITICAL, AlarmUtil.checkCurrentOverload(125, threshold));
        assertEquals(Severities.CRITICAL, AlarmUtil.checkCurrentOverload(140, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, AlarmUtil.checkCurrentOverload(100, threshold));
        assertEquals(Severities.NORMAL, AlarmUtil.checkCurrentOverload(150, threshold));
    }

    @Test
    void shouldCheckPhaseLoss() {
        double threshold = 100;

        // Test CRITICAL
        assertEquals(Severities.CRITICAL, AlarmUtil.checkPhaseLoss(150, threshold));
        assertEquals(Severities.CRITICAL, AlarmUtil.checkPhaseLoss(160, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, AlarmUtil.checkPhaseLoss(140, threshold));
        assertEquals(Severities.NORMAL, AlarmUtil.checkPhaseLoss(95, threshold));
    }

    @Test
    void shouldCheckTemperature() {
        double threshold = 100;

        // Test CRITICAL
        assertEquals(Severities.CRITICAL, AlarmUtil.checkTemperature(100, threshold));
        assertEquals(Severities.CRITICAL, AlarmUtil.checkTemperature(105, threshold));

        // Test WARNING
        assertEquals(Severities.WARNING, AlarmUtil.checkTemperature(90, threshold));
        assertEquals(Severities.WARNING, AlarmUtil.checkTemperature(95, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, AlarmUtil.checkTemperature(85, threshold));
        assertEquals(Severities.NORMAL, AlarmUtil.checkTemperature(80, threshold));
    }

    @Test
    void shouldCheckNullReadings() {
        LocalDateTime timestamp = LocalDateTime.now();
        String id = "1137";
        double[] voltages = {0, 220, 0};
        double[] currents = {0, 100, 0};
        double temperature = 0;

        List<AlarmDTO> alarms = AlarmUtil.checkNullReadings(timestamp, id, voltages, currents, temperature);

        // Expected null reading alarms
        assertEquals(5, alarms.size());
        assertEquals("No Data for Phase 1 Voltage", alarms.get(0).alarm());
        assertEquals(Severities.NULL, alarms.get(0).severity());
        assertEquals("No Data for Phase 3 Voltage", alarms.get(1).alarm());
        assertEquals(Severities.NULL, alarms.get(1).severity());
        assertEquals("No Data for Line 1 Current", alarms.get(2).alarm());
        assertEquals(Severities.NULL, alarms.get(2).severity());
        assertEquals("No Data for Line 3 Current", alarms.get(3).alarm());
        assertEquals(Severities.NULL, alarms.get(3).severity());
        assertEquals(Severities.NULL, alarms.get(3).severity());
        assertEquals("No Data for Temperature", alarms.get(4).alarm());
        assertEquals(Severities.NULL, alarms.get(4).severity());
    }
}
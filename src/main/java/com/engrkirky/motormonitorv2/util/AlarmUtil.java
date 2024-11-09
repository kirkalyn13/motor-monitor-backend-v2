package com.engrkirky.motormonitorv2.util;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlarmUtil {
    public static Severities checkOverVoltage(double voltage, double threshold) {
        if (voltage >= (1.15 * threshold)) return Severities.CRITICAL;
        if (voltage >= (1.1 * threshold)) return Severities.WARNING;

        return Severities.NORMAL;
    }

    public static Severities checkUnderVoltage(double voltage, double threshold) {
        if (voltage <= (0.85 * threshold) && voltage > (0.1 * threshold)) return Severities.CRITICAL;
        if (voltage <= (0.9 * threshold) && voltage > (0.1 * threshold)) return Severities.WARNING;

        return Severities.NORMAL;
    }

    public static Severities checkNoPower(double voltage, double threshold) {
        if (voltage <= (0.1 * threshold)) return Severities.CRITICAL;

        return Severities.NORMAL;
    }

    public static Severities checkShortCircuit(double current, double threshold) {
        if (current >= (threshold * 4)) return Severities.CRITICAL;

        return Severities.NORMAL;
    }

    public static Severities checkCurrentOverload(double current, double threshold) {
        if (current >= (1.25 * threshold) && current < (1.5 * threshold)) return Severities.CRITICAL;

        return Severities.NORMAL;
    }

    public static Severities checkPhaseLoss(double current, double threshold) {
        if (current >= (1.5 * threshold)) return Severities.CRITICAL;

        return Severities.NORMAL;
    }

    public static Severities checkTemperature(double temperature, double threshold) {
        if (temperature >= threshold) return Severities.CRITICAL;
        if (temperature >= (0.9 * threshold)) return Severities.WARNING;

        return Severities.NORMAL;
    }

    public static List<AlarmDTO> checkNullReadings(LocalDateTime timestamp, String motorID, double[] voltages, double[] currents, double temperature) {
        List<AlarmDTO> alarms = new ArrayList<>();

        if (voltages[0] == 0) {
            alarms.add(new AlarmDTO(timestamp, motorID,  "No Data for Phase 1 Voltage", Severities.NULL));
        }
        if (voltages[1] == 0) {
            alarms.add(new AlarmDTO(timestamp, motorID,  "No Data for Phase 2 Voltage", Severities.NULL));
        }
        if (voltages[2] == 0) {
            alarms.add(new AlarmDTO(timestamp, motorID,  "No Data for Phase 3 Voltage", Severities.NULL));
        }
        if (currents[0] == 0) {
            alarms.add(new AlarmDTO(timestamp, motorID,  "No Data for Line 1 Current", Severities.NULL));
        }
        if (currents[1] == 0) {
            alarms.add(new AlarmDTO(timestamp, motorID,  "No Data for Line 2 Current", Severities.NULL));
        }
        if (currents[2] == 0) {
            alarms.add(new AlarmDTO(timestamp, motorID,  "No Data for Line 3 Current", Severities.NULL));
        }
        if (temperature == 0) {
            alarms.add(new AlarmDTO(timestamp, motorID,  "No Data for Temperature", Severities.NULL));
        }

        return alarms;
    }
}


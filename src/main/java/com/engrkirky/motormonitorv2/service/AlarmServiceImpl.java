package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.util.AlarmUtil;
import com.engrkirky.motormonitorv2.util.Severities;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService {
    @Override
    public List<AlarmDTO> analyzeMetrics(MetricsDTO metricsDTO, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        List<AlarmDTO> alarms = new ArrayList<>();

        Severities severity = AlarmUtil.checkOverVoltage(metricsDTO.line1Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Phase 1 Over Voltage", severity));
        }

        severity = AlarmUtil.checkOverVoltage(metricsDTO.line2Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Phase 2 Over Voltage", severity));
        }

        severity = AlarmUtil.checkOverVoltage(metricsDTO.line3Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Phase 3 Over Voltage", severity));
        }

        severity = AlarmUtil.checkUnderVoltage(metricsDTO.line1Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Phase 1 Under Voltage", severity));
        }

        severity = AlarmUtil.checkUnderVoltage(metricsDTO.line2Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Phase 2 Under Voltage", severity));
        }

        severity = AlarmUtil.checkUnderVoltage(metricsDTO.line3Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Phase 3 Under Voltage", severity));
        }

        severity = AlarmUtil.checkNoPower(metricsDTO.line1Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Phase 1 No Power", severity));
        }

        severity = AlarmUtil.checkNoPower(metricsDTO.line2Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Phase 2 No Power", severity));
        }

        severity = AlarmUtil.checkNoPower(metricsDTO.line3Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Phase 3 No Power", severity));
        }

        severity = AlarmUtil.checkShortCircuit(metricsDTO.line1Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Line 1 Short Circuit", severity));
        }

        severity = AlarmUtil.checkShortCircuit(metricsDTO.line2Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Line 2 Short Circuit", severity));
        }

        severity = AlarmUtil.checkShortCircuit(metricsDTO.line3Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Line 3 Short Circuit", severity));
        }

        severity = AlarmUtil.checkCurrentOverload(metricsDTO.line1Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Line 1 Current Overload", severity));
        }

        severity = AlarmUtil.checkCurrentOverload(metricsDTO.line2Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Line 2 Current Overload", severity));
        }

        severity = AlarmUtil.checkCurrentOverload(metricsDTO.line3Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Line 3 Current Overload", severity));
        }

        severity = AlarmUtil.checkPhaseLoss(metricsDTO.line1Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Line 1 Phase Loss", severity));
        }

        severity = AlarmUtil.checkPhaseLoss(metricsDTO.line2Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Line 2 Phase Loss", severity));
        }

        severity = AlarmUtil.checkPhaseLoss(metricsDTO.line3Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Line 3 Phase Loss", severity));
        }

        severity = AlarmUtil.checkTemperature(metricsDTO.temperature(), maxTemperature);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metricsDTO.timestamp(), "Motor Overheating", severity));
        }

        List<AlarmDTO> nullReadings = AlarmUtil.checkNullReadings(
                metricsDTO.timestamp(),
                new double[] {metricsDTO.line1Voltage(), metricsDTO.line2Voltage(), metricsDTO.line3Voltage()},
                new double[] {metricsDTO.line1Current(), metricsDTO.line2Current(), metricsDTO.line3Current()},
                metricsDTO.temperature()
        );
        if (nullReadings.size() > 0) alarms.addAll(nullReadings);

        return alarms;
    }

    private static boolean hasAlarm(Severities severity) {
        return severity != Severities.NORMAL;
    }
}

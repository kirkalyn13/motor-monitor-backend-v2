package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.mapper.AlarmMapper;
import com.engrkirky.motormonitorv2.model.Alarm;
import com.engrkirky.motormonitorv2.repository.AlarmRepository;
import com.engrkirky.motormonitorv2.util.AlarmUtil;
import com.engrkirky.motormonitorv2.util.DateTimeUtil;
import com.engrkirky.motormonitorv2.util.Severities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService {
    private final AlarmRepository alarmRepository;
    private final AlarmMapper alarmMapper;

    @Autowired
    public AlarmServiceImpl(AlarmRepository alarmRepository, AlarmMapper alarmMapper) {
        this.alarmRepository = alarmRepository;
        this.alarmMapper = alarmMapper;
    }

    @Override
    public List<AlarmDTO> getAlarmsHistoryByMotorID(String id, int limit) {
        LocalDateTime[] timestampRange = DateTimeUtil.getTimeRange(limit);

        return alarmRepository
                .findAlarmHistoryByMotorID(id, timestampRange[0], timestampRange[1])
                .stream()
                .map(alarmMapper::convertToDTO)
                .toList();
    }

    @Override
    public List<AlarmDTO> analyzeMetrics(MetricsDTO metricsDTO, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        List<AlarmDTO> alarms = new ArrayList<>();

        Severities severity = AlarmUtil.checkOverVoltage(metricsDTO.line1Voltage(), ratedVoltage);
        AlarmDTO alarmDTO;
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 1 Over Voltage", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkOverVoltage(metricsDTO.line2Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 2 Over Voltage", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkOverVoltage(metricsDTO.line3Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 3 Over Voltage", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkUnderVoltage(metricsDTO.line1Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 1 Under Voltage", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkUnderVoltage(metricsDTO.line2Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 2 Under Voltage", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkUnderVoltage(metricsDTO.line3Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 3 Under Voltage", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkNoPower(metricsDTO.line1Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 1 No Power", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkNoPower(metricsDTO.line2Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 2 No Power", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkNoPower(metricsDTO.line3Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Phase 3 No Power", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkShortCircuit(metricsDTO.line1Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Line 1 Short Circuit", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkShortCircuit(metricsDTO.line2Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Line 2 Short Circuit", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkShortCircuit(metricsDTO.line3Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Line 3 Short Circuit", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkCurrentOverload(metricsDTO.line1Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Line 1 Current Overload", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkCurrentOverload(metricsDTO.line2Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Line 2 Current Overload", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkCurrentOverload(metricsDTO.line3Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Line 3 Current Overload", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkPhaseLoss(metricsDTO.line1Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Line 1 Phase Loss", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkPhaseLoss(metricsDTO.line2Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Line 2 Phase Loss", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkPhaseLoss(metricsDTO.line3Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Line 3 Phase Loss", severity);
            alarms.add(alarmDTO);
        }

        severity = AlarmUtil.checkTemperature(metricsDTO.temperature(), maxTemperature);
        if (hasAlarm(severity)) {
            alarmDTO = new AlarmDTO(metricsDTO.timestamp(), metricsDTO.motorID(), "Motor Overheating", severity);
            alarms.add(alarmDTO);
        }

        List<AlarmDTO> nullReadings = AlarmUtil.checkNullReadings(
                metricsDTO.timestamp(),
                metricsDTO.motorID(),
                new double[] {metricsDTO.line1Voltage(), metricsDTO.line2Voltage(), metricsDTO.line3Voltage()},
                new double[] {metricsDTO.line1Current(), metricsDTO.line2Current(), metricsDTO.line3Current()},
                metricsDTO.temperature()
        );
        if (nullReadings.size() > 0) alarms.addAll(nullReadings);

        return alarms;
    }

    @Override
    public String addAlarms(String id, List<AlarmDTO> alarmDTOs) {
        List<Alarm> alarms = alarmDTOs.stream().map(alarmMapper::convertToEntity).toList();
        alarmRepository.saveAll(alarms);

        return id;
    }

    private static boolean hasAlarm(Severities severity) {
        return severity != Severities.NORMAL;
    }
}

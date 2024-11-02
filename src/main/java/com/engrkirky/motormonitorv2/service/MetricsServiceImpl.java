package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.*;
import com.engrkirky.motormonitorv2.mapper.LatestMetrcisMapper;
import com.engrkirky.motormonitorv2.mapper.MetricsMapper;
import com.engrkirky.motormonitorv2.repository.MetricsRepository;
import com.engrkirky.motormonitorv2.util.AlarmUtil;
import com.engrkirky.motormonitorv2.util.DateTimeUtil;
import com.engrkirky.motormonitorv2.util.Severities;
import com.engrkirky.motormonitorv2.util.StatusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetricsServiceImpl implements MetricsService {
    private final MetricsRepository metricsRepository;
    private final MetricsMapper metricsMapper;
    private final LatestMetrcisMapper latestMetrcisMapper;

    @Autowired
    public MetricsServiceImpl(MetricsRepository metricsRepository, MetricsMapper metricsMapper, LatestMetrcisMapper latestMetrcisMapper) {
        this.metricsRepository = metricsRepository;
        this.metricsMapper = metricsMapper;
        this.latestMetrcisMapper = latestMetrcisMapper;
    }

    @Override
    public LatestMetricsDTO getLatestMetrics(String id, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        return latestMetrcisMapper
                .convertToLatestMetricsDTO(metricsRepository.findLatestMetrics(id),
                        ratedVoltage,
                        ratedCurrent,
                        maxTemperature);
    }

    @Override
    public List<VoltageDTO> getVoltageTrend(String id, int limit) {
        LocalDateTime[] timestampRange = DateTimeUtil.getTimeRange(limit);
        return metricsRepository
                .findVoltageTrend(id, timestampRange[0], timestampRange[1]);
    }

    @Override
    public List<CurrentDTO> getCurrentTrend(String id, int limit) {
        LocalDateTime[] timestampRange = DateTimeUtil.getTimeRange(limit);
        return metricsRepository
                .findCurrentTrend(id, timestampRange[0], timestampRange[1]);
    }

    @Override
    public List<TemperatureDTO> getTemperatureTrend(String id, int limit) {
        LocalDateTime[] timestampRange = DateTimeUtil.getTimeRange(limit);
        return metricsRepository
                .findTemperatureTrend(id, timestampRange[0], timestampRange[1]);
    }

    @Override
    public MetricsSummaryDTO getMetricsSummary(String id, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        LatestMetricsDTO latestMetrics = latestMetrcisMapper
                .convertToLatestMetricsDTO(
                        metricsRepository.findLatestMetrics(id),
                        ratedVoltage,
                        ratedCurrent,
                        maxTemperature);

        List<Severities> metricsStatus = List.of(
                StatusUtil.getVoltageStatus(latestMetrics.line1Voltage().value(), ratedVoltage),
                StatusUtil.getVoltageStatus(latestMetrics.line2Voltage().value(), ratedVoltage),
                StatusUtil.getVoltageStatus(latestMetrics.line3Voltage().value(), ratedVoltage),
                StatusUtil.getVoltageStatus(latestMetrics.line1Current().value(), ratedCurrent),
                StatusUtil.getVoltageStatus(latestMetrics.line2Current().value(), ratedCurrent),
                StatusUtil.getVoltageStatus(latestMetrics.line3Current().value(), ratedCurrent),
                StatusUtil.getVoltageStatus(latestMetrics.temperature().value(), maxTemperature)
        );

        int normalCount = 0, warningCount = 0, criticalCount = 0;
        for (Severities status : metricsStatus) {
            if (status.equals(Severities.NORMAL)) {
                normalCount++;
                continue;
            }
            if (status.equals(Severities.WARNING)) {
                warningCount++;
                continue;
            }
            criticalCount++;
        }

        int[] tally = {normalCount, warningCount, criticalCount};
        return new MetricsSummaryDTO(tally);
    }

    @Override
    public String addMetrics(String motorID, MetricsDTO metricsDTO) {
        metricsRepository.save(metricsMapper.convertToEntity(metricsDTO));
        return motorID;
    }

    @Override
    public List<MetricsDTO> getMetricsLogs(String id, int limit) {
        LocalDateTime[] timestampRange = DateTimeUtil.getTimeRange(limit);
        return metricsRepository
                .findMetricsLogs(id, timestampRange[0], timestampRange[1])
                .stream()
                .map(metricsMapper::convertToDTO)
                .toList();
    }

    @Override
    public List<AlarmDTO> getAlarms(String id, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        List<AlarmDTO> alarms = new ArrayList<>();
        MetricsDTO metrics = metricsMapper.convertToDTO(metricsRepository.findLatestMetrics(id));

        Severities severity = AlarmUtil.checkOverVoltage(metrics.line1Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Phase 1 Over Voltage", severity));
        }

        severity = AlarmUtil.checkOverVoltage(metrics.line2Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Phase 2 Over Voltage", severity));
        }

        severity = AlarmUtil.checkOverVoltage(metrics.line3Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Phase 3 Over Voltage", severity));
        }

        severity = AlarmUtil.checkUnderVoltage(metrics.line1Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Phase 1 Under Voltage", severity));
        }

        severity = AlarmUtil.checkUnderVoltage(metrics.line2Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Phase 2 Under Voltage", severity));
        }

        severity = AlarmUtil.checkUnderVoltage(metrics.line3Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Phase 3 Under Voltage", severity));
        }

        severity = AlarmUtil.checkNoPower(metrics.line1Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Phase 1 No Power", severity));
        }

        severity = AlarmUtil.checkNoPower(metrics.line2Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Phase 2 No Power", severity));
        }

        severity = AlarmUtil.checkNoPower(metrics.line3Voltage(), ratedVoltage);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Phase 3 No Power", severity));
        }

        severity = AlarmUtil.checkShortCircuit(metrics.line1Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Line 1 Short Circuit", severity));
        }

        severity = AlarmUtil.checkShortCircuit(metrics.line2Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Line 2 Short Circuit", severity));
        }

        severity = AlarmUtil.checkShortCircuit(metrics.line3Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Line 3 Short Circuit", severity));
        }

        severity = AlarmUtil.checkCurrentOverload(metrics.line1Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Line 1 Current Overload", severity));
        }

        severity = AlarmUtil.checkCurrentOverload(metrics.line2Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Line 2 Current Overload", severity));
        }

        severity = AlarmUtil.checkCurrentOverload(metrics.line3Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Line 3 Current Overload", severity));
        }

        severity = AlarmUtil.checkPhaseLoss(metrics.line1Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Line 1 Phase Loss", severity));
        }

        severity = AlarmUtil.checkPhaseLoss(metrics.line2Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Line 2 Phase Loss", severity));
        }

        severity = AlarmUtil.checkPhaseLoss(metrics.line3Current(), ratedCurrent);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Line 3 Phase Loss", severity));
        }

        severity = AlarmUtil.checkTemperature(metrics.temperature(), maxTemperature);
        if (hasAlarm(severity)) {
            alarms.add(new AlarmDTO(metrics.timestamp(), "Motor Overheating", severity));
        }

        List<AlarmDTO> nullReadings = AlarmUtil.checkNullReadings(
                metrics.timestamp(),
                new double[] {metrics.line1Voltage(), metrics.line2Voltage(), metrics.line3Voltage()},
                new double[] {metrics.line1Current(), metrics.line2Current(), metrics.line3Current()},
                metrics.temperature()
        );
        if (nullReadings.size() > 0) alarms.addAll(nullReadings);

        return alarms;
    }

    private static boolean hasAlarm(Severities severity) {
        return severity != Severities.NORMAL;
    }
}

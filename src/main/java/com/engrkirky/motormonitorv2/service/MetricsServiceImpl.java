package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.util.DateTimeUtil;
import com.engrkirky.motormonitorv2.dto.*;
import com.engrkirky.motormonitorv2.mapper.*;
import com.engrkirky.motormonitorv2.repository.MetricsRepository;
import com.engrkirky.motormonitorv2.util.Severities;
import com.engrkirky.motormonitorv2.util.StatusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}

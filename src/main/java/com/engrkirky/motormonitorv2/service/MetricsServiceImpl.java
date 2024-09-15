package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.DateTimeUtil;
import com.engrkirky.motormonitorv2.dto.*;
import com.engrkirky.motormonitorv2.mapper.*;
import com.engrkirky.motormonitorv2.repository.MetricsRepository;
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
        return latestMetrcisMapper.convertToLatestMetricsDTO(metricsRepository.findLatestMetrics(id));
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
    public List<Integer> getMetricsSummary(String id, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        return List.of(1,2,3);
    }

    @Override
    public void addMetrics(MetricsDTO metricsDTO) {
        metricsRepository.save(metricsMapper.convertToEntity(metricsDTO));
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

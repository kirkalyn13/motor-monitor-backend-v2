package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.*;
import com.engrkirky.motormonitorv2.mapper.LatestMetrcisMapper;
import com.engrkirky.motormonitorv2.mapper.MetricsMapper;
import com.engrkirky.motormonitorv2.messaging.RabbitMQSender;
import com.engrkirky.motormonitorv2.repository.MetricsRepository;
import com.engrkirky.motormonitorv2.util.DateTimeUtil;
import com.engrkirky.motormonitorv2.util.Severities;
import com.engrkirky.motormonitorv2.util.StatusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementation for managing motor metrics.
 */
@Service
public class MetricsServiceImpl implements MetricsService {
    private final MetricsRepository metricsRepository;
    private final MetricsMapper metricsMapper;
    private final LatestMetrcisMapper latestMetrcisMapper;
    private final AlarmService alarmService;
    private final RabbitMQSender rabbitMQSender;

    @Autowired
    public MetricsServiceImpl(
            MetricsRepository metricsRepository,
            MetricsMapper metricsMapper,
            LatestMetrcisMapper latestMetrcisMapper,
            AlarmService alarmService,
            RabbitMQSender rabbitMQSender) {
        this.metricsRepository = metricsRepository;
        this.metricsMapper = metricsMapper;
        this.latestMetrcisMapper = latestMetrcisMapper;
        this.alarmService = alarmService;
        this.rabbitMQSender = rabbitMQSender;
    }

    /**
     * Retrieves the latest metrics for a motor.
     *
     * @param id motor identifier
     * @param ratedVoltage rated voltage
     * @param ratedCurrent rated current
     * @param maxTemperature maximum temperature
     * @return latest metrics
     */
    @Override
    public LatestMetricsDTO getLatestMetrics(String id, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        return latestMetrcisMapper
                .convertToLatestMetricsDTO(metricsRepository.findLatestMetrics(id),
                        ratedVoltage,
                        ratedCurrent,
                        maxTemperature);
    }

    /**
     * Retrieves voltage trend data.
     *
     * @param id motor identifier
     * @param limit time range in minutes
     * @return voltage trend data
     */
    @Override
    public List<VoltageDTO> getVoltageTrend(String id, int limit) {
        LocalDateTime[] timestampRange = DateTimeUtil.getTimeRange(limit);
        return metricsRepository
                .findVoltageTrend(id, timestampRange[0], timestampRange[1]);
    }

    /**
     * Retrieves current trend data.
     *
     * @param id motor identifier
     * @param limit time range in minutes
     * @return current trend data
     */
    @Override
    public List<CurrentDTO> getCurrentTrend(String id, int limit) {
        LocalDateTime[] timestampRange = DateTimeUtil.getTimeRange(limit);
        return metricsRepository
                .findCurrentTrend(id, timestampRange[0], timestampRange[1]);
    }

    /**
     * Retrieves temperature trend data.
     *
     * @param id motor identifier
     * @param limit time range in minutes
     * @return temperature trend data
     */
    @Override
    public List<TemperatureDTO> getTemperatureTrend(String id, int limit) {
        LocalDateTime[] timestampRange = DateTimeUtil.getTimeRange(limit);
        return metricsRepository
                .findTemperatureTrend(id, timestampRange[0], timestampRange[1]);
    }

    /**
     * Retrieves a summary of motor metrics.
     *
     * @param id motor identifier
     * @param ratedVoltage rated voltage
     * @param ratedCurrent rated current
     * @param maxTemperature maximum temperature
     * @return metrics summary
     */
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

    /**
     * Publishes metrics and generated alarms.
     *
     * @param motorID motor identifier
     * @param metricsDTO metrics data
     * @param ratedVoltage rated voltage
     * @param ratedCurrent rated current
     * @param maxTemperature maximum temperature
     * @return motor identifier
     */
    @Override
    public String publishMetrics(String motorID, MetricsDTO metricsDTO, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        rabbitMQSender.sendMetricsMessage(metricsDTO);
        List<AlarmDTO> alarms = alarmService.analyzeMetrics(metricsDTO, ratedVoltage, ratedCurrent, maxTemperature);
        if (!alarms.isEmpty()) {
            rabbitMQSender.sendAlarmMessage(alarms);
        }

        return motorID;
    }

    /**
     * Stores a metrics record.
     *
     * @param motorID motor identifier
     * @param metricsDTO metrics data
     * @return motor identifier
     */
    @Override
    public String addMetrics(String motorID, MetricsDTO metricsDTO) {
        metricsRepository.save(metricsMapper.convertToEntity(metricsDTO));
        return motorID;
    }

    /**
     * Retrieves metrics logs.
     *
     * @param id motor identifier
     * @param limit time range in minutes
     * @return metrics logs
     */
    @Override
    public List<MetricsDTO> getMetricsLogs(String id, int limit) {
        LocalDateTime[] timestampRange = DateTimeUtil.getTimeRange(limit);
        return metricsRepository
                .findMetricsLogs(id, timestampRange[0], timestampRange[1])
                .stream()
                .map(metricsMapper::convertToDTO)
                .toList();
    }

    /**
     * Retrieves alarms based on the latest metrics.
     *
     * @param id motor identifier
     * @param ratedVoltage rated voltage
     * @param ratedCurrent rated current
     * @param maxTemperature maximum temperature
     * @return list of alarms
     */
    @Override
    public List<AlarmDTO> getAlarms(String id, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        MetricsDTO metrics = metricsMapper.convertToDTO(metricsRepository.findLatestMetrics(id));

        return alarmService.analyzeMetrics(metrics, ratedVoltage, ratedCurrent, maxTemperature);
    }
}

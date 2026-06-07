package com.engrkirky.motormonitorv2.controller;

import com.engrkirky.motormonitorv2.dto.*;
import com.engrkirky.motormonitorv2.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for motor metrics operations.
 */
@RestController
@RequestMapping("/api/v2/metrics")
public class MetricsController {
    private final MetricsService metricsService;

    @Autowired
    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    /**
     * Retrieves the latest metrics for a motor.
     *
     * @param id motor identifier
     * @param ratedVoltage rated motor voltage
     * @param ratedCurrent rated motor current
     * @param maxTemperature maximum allowed temperature
     * @return latest metrics
     */
    @GetMapping("/{id}")
    public ResponseEntity<LatestMetricsDTO> getLatestMetrics(
            @PathVariable("id") String id,
            @RequestParam("ratedVoltage") double ratedVoltage,
            @RequestParam("ratedCurrent") double ratedCurrent,
            @RequestParam("maxTemperature") double maxTemperature
    ) {
        LatestMetricsDTO result = metricsService.getLatestMetrics(id, ratedVoltage, ratedCurrent,maxTemperature);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieves a summary of motor metrics.
     *
     * @param id motor identifier
     * @param ratedVoltage rated motor voltage
     * @param ratedCurrent rated motor current
     * @param maxTemperature maximum allowed temperature
     * @return metrics summary
     */
    @GetMapping("/{id}/summary")
    public ResponseEntity<MetricsSummaryDTO> getMetricsSummary(
            @PathVariable("id") String id,
            @RequestParam("ratedVoltage") double ratedVoltage,
            @RequestParam("ratedCurrent") double ratedCurrent,
            @RequestParam("maxTemperature") double maxTemperature
    ) {
        MetricsSummaryDTO result = metricsService.getMetricsSummary(id, ratedVoltage, ratedCurrent,maxTemperature);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieves voltage trend data.
     *
     * @param id motor identifier
     * @param limit number of records to retrieve
     * @return voltage trend data
     */
    @GetMapping("/{id}/voltage")
    public ResponseEntity<List<VoltageDTO>> getVoltageTrend(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<VoltageDTO> results = metricsService.getVoltageTrend(id, limit);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Retrieves current trend data.
     *
     * @param id motor identifier
     * @param limit number of records to retrieve
     * @return current trend data
     */
    @GetMapping("/{id}/current")
    public ResponseEntity<List<CurrentDTO>> getCurrentTrend(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<CurrentDTO> results = metricsService.getCurrentTrend(id, limit);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Retrieves temperature trend data.
     *
     * @param id motor identifier
     * @param limit number of records to retrieve
     * @return temperature trend data
     */
    @GetMapping("/{id}/temperature")
    public ResponseEntity<List<TemperatureDTO>> getTemperatureTrend(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<TemperatureDTO> results = metricsService.getTemperatureTrend(id, limit);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * Adds a new metrics record.
     *
     * @param motorID motor identifier
     * @param line1Voltage line 1 voltage
     * @param line2Voltage line 2 voltage
     * @param line3Voltage line 3 voltage
     * @param line1Current line 1 current
     * @param line2Current line 2 current
     * @param line3Current line 3 current
     * @param temperature motor temperature
     * @param ratedVoltage rated motor voltage
     * @param ratedCurrent rated motor current
     * @param maxTemperature maximum allowed temperature
     * @return operation result
     */
    @PostMapping("/{id}")
    public ResponseEntity<String> addMetrics(
            @PathVariable("id") String motorID,
            @RequestParam("line1Voltage") double line1Voltage,
            @RequestParam("line2Voltage") double line2Voltage,
            @RequestParam("line3Voltage") double line3Voltage,
            @RequestParam("line1Current") double line1Current,
            @RequestParam("line2Current") double line2Current,
            @RequestParam("line3Current") double line3Current,
            @RequestParam("temperature") double temperature,
            @RequestParam("ratedVoltage") double ratedVoltage,
            @RequestParam("ratedCurrent") double ratedCurrent,
            @RequestParam("maxTemperature") double maxTemperature
    ) {
        MetricsDTO newMetrics = new MetricsDTO(
                null,
                LocalDateTime.now(),
                motorID,
                line1Voltage,
                line2Voltage,
                line3Voltage,
                line1Current,
                line2Current,
                line3Current,
                temperature
                );
        String result = metricsService.publishMetrics(motorID, newMetrics, ratedVoltage, ratedCurrent, maxTemperature);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Retrieves metrics logs for a motor.
     *
     * @param id motor identifier
     * @param limit number of records to retrieve
     * @return metrics logs
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<List<MetricsDTO>> getMetricsLogs(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<MetricsDTO> results = metricsService.getMetricsLogs(id, limit);

        return new ResponseEntity<>(results,HttpStatus.OK);
    }

    /**
     * Retrieves active alarms for a motor.
     *
     * @param id motor identifier
     * @param ratedVoltage rated motor voltage
     * @param ratedCurrent rated motor current
     * @param maxTemperature maximum allowed temperature
     * @return list of alarms
     */
    @GetMapping("/{id}/alarms")
    public ResponseEntity<List<AlarmDTO>> getAlarms(
            @PathVariable("id") String id,
            @RequestParam("ratedVoltage") double ratedVoltage,
            @RequestParam("ratedCurrent") double ratedCurrent,
            @RequestParam("maxTemperature") double maxTemperature
        ) {
        List<AlarmDTO> results = metricsService.getAlarms(id, ratedVoltage, ratedCurrent, maxTemperature);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}

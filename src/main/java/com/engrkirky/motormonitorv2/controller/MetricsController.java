package com.engrkirky.motormonitorv2.controller;

import com.engrkirky.motormonitorv2.dto.*;
import com.engrkirky.motormonitorv2.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v2/metrics")
public class MetricsController {
    private final MetricsService metricsService;

    @Autowired
    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

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

    @GetMapping("/{id}/voltage")
    public ResponseEntity<List<VoltageDTO>> getVoltageTrend(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<VoltageDTO> results = metricsService.getVoltageTrend(id, limit);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}/current")
    public ResponseEntity<List<CurrentDTO>> getCurrentTrend(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<CurrentDTO> results = metricsService.getCurrentTrend(id, limit);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}/temperature")
    public ResponseEntity<List<TemperatureDTO>> getTemperatureTrend(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<TemperatureDTO> results = metricsService.getTemperatureTrend(id, limit);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


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
        String result = metricsService.addMetrics(motorID, newMetrics, ratedVoltage, ratedCurrent, maxTemperature);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<List<MetricsDTO>> getMetricsLogs(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<MetricsDTO> results = metricsService.getMetricsLogs(id, limit);

        return new ResponseEntity<>(results,HttpStatus.OK);
    }

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

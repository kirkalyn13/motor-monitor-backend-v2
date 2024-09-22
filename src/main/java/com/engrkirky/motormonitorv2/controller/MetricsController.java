package com.engrkirky.motormonitorv2.controller;

import com.engrkirky.motormonitorv2.dto.LatestMetricsDTO;
import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.dto.MetricsSummaryDTO;
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

    @GetMapping("/summary/{id}")
    public ResponseEntity<MetricsSummaryDTO> getMetricsSummary(
            @PathVariable("id") String id,
            @RequestParam("ratedVoltage") double ratedVoltage,
            @RequestParam("ratedCurrent") double ratedCurrent,
            @RequestParam("maxTemperature") double maxTemperature
    ) {
        MetricsSummaryDTO result = metricsService.getMetricsSummary(id, ratedVoltage, ratedCurrent,maxTemperature);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
            @RequestParam("temperature") double temperature
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
        String result = metricsService.addMetrics(motorID, newMetrics);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<List<MetricsDTO>> getMetricsLogs(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<MetricsDTO> results = metricsService.getMetricsLogs(id, limit);

        return new ResponseEntity<>(results,HttpStatus.OK);
    }
}

package com.engrkirky.motormonitorv2.controller;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/alarms")
public class AlarmController {
    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AlarmDTO>> getAlarmsHistoryByID(
            @PathVariable("id") String id,
            @RequestParam("period") int limit
    ) {
        List<AlarmDTO> results = alarmService.getAlarmsHistoryByMotorID(id, limit);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}

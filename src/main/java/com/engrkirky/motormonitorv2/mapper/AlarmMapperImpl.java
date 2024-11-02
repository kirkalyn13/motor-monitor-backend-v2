package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.model.Alarm;
import org.springframework.stereotype.Component;

@Component
public class AlarmMapperImpl implements AlarmMapper {
    @Override
    public AlarmDTO convertToDTO(Alarm alarm) {
        return new AlarmDTO(
                alarm.getTimestamp(),
                alarm.getMotorID(),
                alarm.getAlarm(),
                alarm.getSeverity()
        );
    }

    @Override
    public Alarm convertToEntity(AlarmDTO alarmDTO) {
        return Alarm.builder()
                .timestamp(alarmDTO.timestamp())
                .motorID(alarmDTO.motorID())
                .alarm(alarmDTO.alarm())
                .severity(alarmDTO.severity())
                .build();
    }
}

package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.model.Alarm;
import org.springframework.stereotype.Component;

/**
 * Mapper implementation for converting between Alarm entities and DTOs.
 */
@Component
public class AlarmMapperImpl implements AlarmMapper {
    /**
     * Converts an Alarm entity to a DTO.
     *
     * @param alarm source entity
     * @return mapped DTO
     */
    @Override
    public AlarmDTO convertToDTO(Alarm alarm) {
        return new AlarmDTO(
                alarm.getTimestamp(),
                alarm.getMotorID(),
                alarm.getAlarm(),
                alarm.getSeverity()
        );
    }

    /**
     * Converts an Alarm DTO to an entity.
     *
     * @param alarmDTO source DTO
     * @return mapped entity
     */
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

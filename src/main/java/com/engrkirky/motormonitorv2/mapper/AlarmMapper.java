package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.model.Alarm;

/**
 * Mapper for converting between Alarm entities and DTOs.
 */
public interface AlarmMapper {
    /**
     * Converts an Alarm entity to a DTO.
     *
     * @param alarm source entity
     * @return mapped DTO
     */
    AlarmDTO convertToDTO(Alarm alarm);

    /**
     * Converts an Alarm DTO to an entity.
     *
     * @param alarmDTO source DTO
     * @return mapped entity
     */
    Alarm convertToEntity(AlarmDTO alarmDTO);
}
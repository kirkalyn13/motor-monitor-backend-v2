package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.model.Alarm;

public interface AlarmMapper {
    AlarmDTO convertToDTO(Alarm alarm);
    Alarm convertToEntity(AlarmDTO alarmDTO);
}
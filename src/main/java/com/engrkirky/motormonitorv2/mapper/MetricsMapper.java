package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.model.Metrics;

/**
 * Mapper for converting between Metrics entity and DTO.
 */
public interface MetricsMapper {

    /**
     * Converts Metrics entity to DTO.
     *
     * @param metrics source entity
     * @return mapped DTO
     */
    MetricsDTO convertToDTO(Metrics metrics);

    /**
     * Converts Metrics DTO to entity.
     *
     * @param metricsDTO source DTO
     * @return mapped entity
     */
    Metrics convertToEntity(MetricsDTO metricsDTO);
}

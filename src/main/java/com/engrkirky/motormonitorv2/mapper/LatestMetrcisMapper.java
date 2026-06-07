package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.LatestMetricsDTO;
import com.engrkirky.motormonitorv2.model.Metrics;

/**
 * Mapper for converting Metrics entity to enriched LatestMetricsDTO.
 */
public interface LatestMetrcisMapper {
    /**
     * Converts a Metrics entity into LatestMetricsDTO with status evaluation.
     *
     * @param metrics source metrics entity
     * @param ratedVoltage rated voltage threshold
     * @param ratedCurrent rated current threshold
     * @param maxTemperature maximum temperature threshold
     * @return enriched latest metrics DTO
     */
    LatestMetricsDTO convertToLatestMetricsDTO(Metrics metrics, double ratedVoltage, double ratedCurrent, double maxTemperature);
}

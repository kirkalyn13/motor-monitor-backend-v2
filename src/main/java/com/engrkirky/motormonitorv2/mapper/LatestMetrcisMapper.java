package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.LatestMetricsDTO;
import com.engrkirky.motormonitorv2.model.Metrics;

public interface LatestMetrcisMapper {
    LatestMetricsDTO convertToLatestMetricsDTO(Metrics metrics);
}

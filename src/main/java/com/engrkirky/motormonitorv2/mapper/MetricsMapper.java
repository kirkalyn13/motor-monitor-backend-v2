package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.model.Metrics;

public interface MetricsMapper {
    MetricsDTO convertToDTO(Metrics metrics);
    Metrics convertToEntity(MetricsDTO metricsDTO);
}

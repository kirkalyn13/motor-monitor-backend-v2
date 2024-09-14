package com.engrkirky.motormonitorv2.repository;

import com.engrkirky.motormonitorv2.model.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Long> {
}

package com.engrkirky.motormonitorv2.repository;

import com.engrkirky.motormonitorv2.dto.CurrentDTO;
import com.engrkirky.motormonitorv2.dto.TemperatureDTO;
import com.engrkirky.motormonitorv2.dto.VoltageDTO;
import com.engrkirky.motormonitorv2.model.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Long> {
    @Query("SELECT m FROM Metrics m WHERE m.motorID = :id ORDER BY m.timestamp DESC LIMIT 1")
    Metrics findLatestMetrics(@Param("id") String id);

    @Query("SELECT m.timestamp, m.line1Voltage, m.line2Voltage, m.line3Voltage FROM Metrics m WHERE m.motorID = :id AND m.timestamp BETWEEN :start AND :end ORDER BY m.timestamp DESC")
    List<VoltageDTO> findVoltageTrend(@Param("id") String id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT m.timestamp, m.line1Current, m.line2Current, m.line3Current FROM Metrics m WHERE m.motorID = :id AND m.timestamp BETWEEN :start AND :end ORDER BY m.timestamp DESC")
    List<CurrentDTO> findCurrentTrend(@Param("id") String id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT m.timestamp, m.temperature FROM Metrics m WHERE m.motorID = :id AND m.timestamp BETWEEN :start AND :end ORDER BY m.timestamp DESC")
    List<TemperatureDTO> findTemperatureTrend(@Param("id") String id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT m FROM Metrics m WHERE m.motorID = :id AND m.timestamp BETWEEN :start AND :end ORDER BY m.timestamp DESC")
    List<Metrics> findMetricsLogs(@Param("id") String id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}

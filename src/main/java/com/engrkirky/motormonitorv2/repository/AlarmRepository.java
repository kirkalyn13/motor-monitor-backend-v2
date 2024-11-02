package com.engrkirky.motormonitorv2.repository;

import com.engrkirky.motormonitorv2.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @Query("SELECT a " +
            "FROM Alarm a " +
            "WHERE a.motorID = :id " +
            "AND a.timestamp BETWEEN :start AND :end " +
            "ORDER BY a.timestamp DESC")
    List<Alarm> findAlarmHistoryByMotorID(@Param("id") String id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}

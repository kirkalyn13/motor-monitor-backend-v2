package com.engrkirky.motormonitorv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "metrics")
@Builder
public class Metrics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "timestamp", updatable = false)
    private LocalDateTime timestamp;

    @Column(name = "motor_id")
    private String motorID;

    @Column(name = "line1_voltage")
    private double line1Voltage;

    @Column(name = "line2_voltage")
    private double line2Voltage;

    @Column(name = "line3_voltage")
    private double line3Voltage;

    @Column(name = "line1_current")
    private double line1Current;

    @Column(name = "line2_current")
    private double line2Current;

    @Column(name = "line3_current")
    private double line3Current;

    @Column(name = "temperature")
    private double temperature;
}

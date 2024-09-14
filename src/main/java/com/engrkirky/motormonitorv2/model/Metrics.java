package com.engrkirky.motormonitorv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "metrics")
@Builder
public class Metrics {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "motor_id")
    private int motorID;

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

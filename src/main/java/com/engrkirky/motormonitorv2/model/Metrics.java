package com.engrkirky.motormonitorv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Date timestamp;

    @Column(name = "motor_id")
    private Integer motorID;

    @Column(name = "line1_voltage")
    private Double line1Voltage;

    @Column(name = "line2_voltage")
    private Double line2Voltage;

    @Column(name = "line3_voltage")
    private Double line3Voltage;

    @Column(name = "line1_current")
    private Double line1Current;

    @Column(name = "line2_current")
    private Double line2Current;

    @Column(name = "line3_current")
    private Double line3Current;

    @Column(name = "temperature")
    private Double temperature;
}

package com.engrkirky.motormonitorv2.model;

import com.engrkirky.motormonitorv2.util.Severities;
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
@Table(name = "alarms")
@Builder
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "timestamp", updatable = false)
    private LocalDateTime timestamp;

    @Column(name = "motor_id")
    private String motorID;

    @Column(name = "alarm")
    private String alarm;

    @Column(name = "severity")
    private Severities severity;
}

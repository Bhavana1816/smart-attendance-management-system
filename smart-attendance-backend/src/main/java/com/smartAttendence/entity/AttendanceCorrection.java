package com.smartAttendence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_corrections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCorrection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attendance_record_id")
    private AttendanceRecord attendanceRecord;

    @ManyToOne
    @JoinColumn(name = "requested_by")
    private User requestedBy;

    private String oldStatus;

    private String newStatus;

    private String reason;

    private String correctionStatus;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    private LocalDateTime reviewedAt;

    private LocalDateTime createdAt;
}
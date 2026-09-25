package com.smartAttendence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CorrectionResponse {

    private Long id;
    private Long attendanceRecordId;

    private String studentName;
    private String subject;

    private String oldStatus;
    private String newStatus;

    private String reason;
    private String status;

    private LocalDateTime createdAt;
}

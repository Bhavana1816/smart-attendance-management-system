package com.smartAttendence.dto;

import lombok.Data;

@Data
public class CorrectionRequest {

    private Long attendanceRecordId;
    private String newStatus;
    private String reason;}

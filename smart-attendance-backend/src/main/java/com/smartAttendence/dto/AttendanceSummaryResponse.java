package com.smartAttendence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceSummaryResponse {

    private Long subjectId;
    private String subjectName;

    private long totalClasses;
    private long presentClasses;
    private long absentClasses;

    private double percentage;

    private boolean lowAttendance;
}

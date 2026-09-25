package com.smartAttendence.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateSessionRequest {

    private Long subjectId;
    private Long sectionId;
    private LocalDate attendanceDate;
    private Integer periodNumber;
}
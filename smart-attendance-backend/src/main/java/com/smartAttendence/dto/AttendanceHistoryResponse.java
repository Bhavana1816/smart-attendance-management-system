package com.smartAttendence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AttendanceHistoryResponse {

    private Long recordId;
    private LocalDate date;
    private String subject;
    private String section;
    private Integer period;
    private String status;
}

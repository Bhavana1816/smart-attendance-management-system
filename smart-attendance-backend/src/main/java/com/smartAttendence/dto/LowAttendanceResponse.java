package com.smartAttendence.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LowAttendanceResponse {

    private String studentName;
    private String registerNumber;
    private String subject;

    private long totalClasses;
    private long presentClasses;

    private double percentage;
}
package com.smartAttendence.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveAttendanceRequest {

    private Long sessionId;
    private List<AttendanceRecordRequest> records;
}

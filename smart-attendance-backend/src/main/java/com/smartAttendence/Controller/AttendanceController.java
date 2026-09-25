package com.smartAttendence.Controller;

import com.smartAttendence.dto.*;
import com.smartAttendence.service.AttendanceService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(
            AttendanceService attendanceService
    ) {
        this.attendanceService =
                attendanceService;
    }

    @PostMapping("/session")
    @PreAuthorize("hasRole('FACULTY')")
    public Long createSession(
            Authentication authentication,
            @RequestBody CreateSessionRequest request
    ) {

        return attendanceService.createSession(
                authentication.getName(),
                request
        );
    }

    @PostMapping("/records")
    @PreAuthorize("hasRole('FACULTY')")
    public String saveAttendance(
            Authentication authentication,
            @RequestBody SaveAttendanceRequest request
    ) {

        attendanceService.saveAttendance(
                authentication.getName(),
                request
        );

        return "Attendance saved successfully";
    }

    @GetMapping("/student/history")
    @PreAuthorize("hasRole('STUDENT')")
    public List<AttendanceHistoryResponse>
    studentHistory(
            Authentication authentication
    ) {

        return attendanceService.studentHistory(
                authentication.getName()
        );
    }
}
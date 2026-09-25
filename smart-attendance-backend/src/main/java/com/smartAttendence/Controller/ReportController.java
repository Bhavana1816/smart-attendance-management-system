package com.smartAttendence.Controller;

import com.smartAttendence.dto.*;
import com.smartAttendence.service.ReportService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(
            ReportService reportService
    ) {
        this.reportService = reportService;
    }

    @GetMapping("/student/summary")
    @PreAuthorize("hasRole('STUDENT')")
    public List<AttendanceSummaryResponse>
    studentSummary(
            Authentication authentication
    ) {

        return reportService.studentSummary(
                authentication.getName()
        );
    }

    @GetMapping("/low-attendance")
    @PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
    public List<LowAttendanceResponse>
    lowAttendance() {

        return reportService.lowAttendance();
    }
}
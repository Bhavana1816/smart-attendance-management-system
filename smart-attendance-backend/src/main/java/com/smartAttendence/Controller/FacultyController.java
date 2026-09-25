package com.smartAttendence.Controller;


import com.smartAttendence.dto.FacultyAssignmentResponse;
import com.smartAttendence.entity.Student;
import com.smartAttendence.repository.StudentRepository;
import com.smartAttendence.service.AttendanceService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    private final AttendanceService attendanceService;
    private final StudentRepository studentRepository;

    public FacultyController(
            AttendanceService attendanceService,
            StudentRepository studentRepository
    ) {
        this.attendanceService = attendanceService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/assignments")
    @PreAuthorize("hasRole('FACULTY')")
    public List<FacultyAssignmentResponse>
    assignments(Authentication authentication) {

        return attendanceService.getAssignments(
                authentication.getName()
        );
    }

    @GetMapping("/students/{sectionId}")
    @PreAuthorize("hasRole('FACULTY')")
    public List<Student> students(
            @PathVariable Long sectionId
    ) {

        return studentRepository
                .findBySectionId(sectionId);
    }
}
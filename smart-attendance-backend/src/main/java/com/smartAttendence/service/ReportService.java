package com.smartAttendence.service;

import com.smartAttendence.dto.*;
import com.smartAttendence.entity.*;
import com.smartAttendence.repository.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRecordRepository recordRepository;

    @Value("${app.attendance.threshold}")
    private double threshold;

    public ReportService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            AttendanceRecordRepository recordRepository
    ) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.recordRepository = recordRepository;
    }

    public List<AttendanceSummaryResponse>
    studentSummary(String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        Student student =
                studentRepository
                        .findByUserId(user.getId())
                        .orElseThrow();

        List<AttendanceRecord> records =
                recordRepository
                        .findByStudentId(student.getId());

        Map<Long, List<AttendanceRecord>> groups =
                new LinkedHashMap<>();

        for (AttendanceRecord record : records) {

            Long subjectId =
                    record.getSession()
                            .getSubject()
                            .getId();

            groups
                    .computeIfAbsent(
                            subjectId,
                            key -> new ArrayList<>()
                    )
                    .add(record);
        }

        List<AttendanceSummaryResponse> result =
                new ArrayList<>();

        for (var entry : groups.entrySet()) {

            List<AttendanceRecord> list =
                    entry.getValue();

            String subjectName =
                    list.get(0)
                            .getSession()
                            .getSubject()
                            .getName();

            long total = list.size();

            long present =
                    list.stream()
                            .filter(record ->
                                    "PRESENT".equals(
                                            record.getStatus()
                                    )
                            )
                            .count();

            long absent =
                    total - present;

            double percentage =
                    total == 0
                            ? 0
                            : present * 100.0 / total;

            result.add(
                    new AttendanceSummaryResponse(
                            entry.getKey(),
                            subjectName,
                            total,
                            present,
                            absent,
                            Math.round(
                                    percentage * 100
                            ) / 100.0,
                            percentage < threshold
                    )
            );
        }

        return result;
    }

    public List<LowAttendanceResponse>
    lowAttendance() {

        List<AttendanceRecord> records =
                recordRepository.findAll();

        Map<String, List<AttendanceRecord>> groups =
                new LinkedHashMap<>();

        for (AttendanceRecord record : records) {

            String key =
                    record.getStudent().getId()
                            + "-"
                            + record.getSession()
                            .getSubject()
                            .getId();

            groups
                    .computeIfAbsent(
                            key,
                            k -> new ArrayList<>()
                    )
                    .add(record);
        }

        List<LowAttendanceResponse> result =
                new ArrayList<>();

        for (List<AttendanceRecord> list :
                groups.values()) {

            AttendanceRecord first =
                    list.get(0);

            long total = list.size();

            long present =
                    list.stream()
                            .filter(record ->
                                    "PRESENT".equals(
                                            record.getStatus()
                                    )
                            )
                            .count();

            double percentage =
                    present * 100.0 / total;

            if (percentage < threshold) {

                result.add(
                        new LowAttendanceResponse(
                                first.getStudent().getName(),
                                first.getStudent()
                                        .getRegisterNumber(),
                                first.getSession()
                                        .getSubject()
                                        .getName(),
                                total,
                                present,
                                Math.round(
                                        percentage * 100
                                ) / 100.0
                        )
                );
            }
        }

        return result;
    }
}

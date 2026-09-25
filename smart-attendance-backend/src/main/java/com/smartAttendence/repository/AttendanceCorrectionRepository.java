package com.smartAttendence.repository;

import com.smartAttendence.entity.AttendanceCorrection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceCorrectionRepository
        extends JpaRepository<AttendanceCorrection, Long> {

    List<AttendanceCorrection>
    findByCorrectionStatus(String correctionStatus);
}

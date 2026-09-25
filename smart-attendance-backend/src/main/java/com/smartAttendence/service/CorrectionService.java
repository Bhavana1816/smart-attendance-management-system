package com.smartAttendence.service;

import com.smartAttendence.dto.*;
import com.smartAttendence.entity.*;
import com.smartAttendence.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CorrectionService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRecordRepository recordRepository;
    private final AttendanceCorrectionRepository correctionRepository;

    public CorrectionService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            AttendanceRecordRepository recordRepository,
            AttendanceCorrectionRepository correctionRepository
    ) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.recordRepository = recordRepository;
        this.correctionRepository = correctionRepository;
    }

    @Transactional
    public void createRequest(
            String email,
            CorrectionRequest request
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        Student student =
                studentRepository
                        .findByUserId(user.getId())
                        .orElseThrow();

        AttendanceRecord record =
                recordRepository
                        .findById(
                                request.getAttendanceRecordId()
                        )
                        .orElseThrow();

        if (!record.getStudent()
                .getId()
                .equals(student.getId())) {

            throw new RuntimeException(
                    "You cannot request this correction"
            );
        }

        AttendanceCorrection correction =
                new AttendanceCorrection();

        correction.setAttendanceRecord(record);
        correction.setRequestedBy(user);
        correction.setOldStatus(record.getStatus());
        correction.setNewStatus(
                request.getNewStatus()
        );
        correction.setReason(
                request.getReason()
        );
        correction.setCorrectionStatus(
                "PENDING"
        );
        correction.setCreatedAt(
                LocalDateTime.now()
        );

        correctionRepository.save(correction);
    }

    public List<CorrectionResponse>
    pendingRequests() {

        return correctionRepository
                .findByCorrectionStatus("PENDING")
                .stream()
                .map(correction -> {

                    AttendanceRecord record =
                            correction.getAttendanceRecord();

                    return new CorrectionResponse(
                            correction.getId(),
                            record.getId(),
                            record.getStudent().getName(),
                            record.getSession()
                                    .getSubject()
                                    .getName(),
                            correction.getOldStatus(),
                            correction.getNewStatus(),
                            correction.getReason(),
                            correction.getCorrectionStatus(),
                            correction.getCreatedAt()
                    );
                })
                .toList();
    }

    @Transactional
    public void approve(Long id) {

        AttendanceCorrection correction =
                correctionRepository
                        .findById(id)
                        .orElseThrow();

        correction
                .getAttendanceRecord()
                .setStatus(
                        correction.getNewStatus()
                );

        correction.setCorrectionStatus(
                "APPROVED"
        );

        correction.setReviewedAt(
                LocalDateTime.now()
        );

        correctionRepository.save(
                correction
        );
    }

    @Transactional
    public void reject(Long id) {

        AttendanceCorrection correction =
                correctionRepository
                        .findById(id)
                        .orElseThrow();

        correction.setCorrectionStatus(
                "REJECTED"
        );

        correction.setReviewedAt(
                LocalDateTime.now()
        );

        correctionRepository.save(
                correction
        );
    }
}

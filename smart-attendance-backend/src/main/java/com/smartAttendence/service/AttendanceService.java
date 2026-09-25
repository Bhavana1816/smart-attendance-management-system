package com.smartAttendence.service;

import com.smartAttendence.dto.*;
import com.smartAttendence.entity.*;
import com.smartAttendence.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final SectionRepository sectionRepository;
    private final FacultySubjectRepository facultySubjectRepository;
    private final AttendanceSessionRepository sessionRepository;
    private final AttendanceRecordRepository recordRepository;

    public AttendanceService(
            UserRepository userRepository,
            FacultyRepository facultyRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            SectionRepository sectionRepository,
            FacultySubjectRepository facultySubjectRepository,
            AttendanceSessionRepository sessionRepository,
            AttendanceRecordRepository recordRepository
    ) {
        this.userRepository = userRepository;
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.sectionRepository = sectionRepository;
        this.facultySubjectRepository = facultySubjectRepository;
        this.sessionRepository = sessionRepository;
        this.recordRepository = recordRepository;
    }

    public List<FacultyAssignmentResponse>
    getAssignments(String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        Faculty faculty =
                facultyRepository
                        .findByUserId(user.getId())
                        .orElseThrow();

        return facultySubjectRepository
                .findByFacultyId(faculty.getId())
                .stream()
                .map(item ->
                        new FacultyAssignmentResponse(
                                item.getSubject().getId(),
                                item.getSubject().getCode(),
                                item.getSubject().getName(),
                                item.getSection().getId(),
                                item.getSection().getName(),
                                item.getSection().getSemester()
                        )
                )
                .toList();
    }

    @Transactional
    public Long createSession(
            String email,
            CreateSessionRequest request
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        Faculty faculty =
                facultyRepository
                        .findByUserId(user.getId())
                        .orElseThrow();

        boolean assigned =
                facultySubjectRepository
                        .existsByFacultyIdAndSubjectIdAndSectionId(
                                faculty.getId(),
                                request.getSubjectId(),
                                request.getSectionId()
                        );

        if (!assigned) {
            throw new RuntimeException(
                    "Faculty is not assigned to this class"
            );
        }

        var existing =
                sessionRepository
                        .findBySubjectIdAndSectionIdAndAttendanceDateAndPeriodNumber(
                                request.getSubjectId(),
                                request.getSectionId(),
                                request.getAttendanceDate(),
                                request.getPeriodNumber()
                        );

        if (existing.isPresent()) {
            return existing.get().getId();
        }

        Subject subject =
                subjectRepository
                        .findById(
                                request.getSubjectId()
                        )
                        .orElseThrow();

        Section section =
                sectionRepository
                        .findById(
                                request.getSectionId()
                        )
                        .orElseThrow();

        AttendanceSession session =
                new AttendanceSession();

        session.setSubject(subject);
        session.setSection(section);
        session.setFaculty(faculty);
        session.setAttendanceDate(
                request.getAttendanceDate()
        );
        session.setPeriodNumber(
                request.getPeriodNumber()
        );
        session.setCreatedAt(
                LocalDateTime.now()
        );

        return sessionRepository
                .save(session)
                .getId();
    }

    @Transactional
    public void saveAttendance(
            String email,
            SaveAttendanceRequest request
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        Faculty faculty =
                facultyRepository
                        .findByUserId(user.getId())
                        .orElseThrow();

        AttendanceSession session =
                sessionRepository
                        .findById(request.getSessionId())
                        .orElseThrow();

        if (!session.getFaculty()
                .getId()
                .equals(faculty.getId())) {

            throw new RuntimeException(
                    "You cannot modify this session"
            );
        }

        for (AttendanceRecordRequest item :
                request.getRecords()) {

            Student student =
                    studentRepository
                            .findById(
                                    item.getStudentId()
                            )
                            .orElseThrow();

            if (!student.getSection()
                    .getId()
                    .equals(
                            session.getSection().getId()
                    )) {

                throw new RuntimeException(
                        "Student does not belong to section"
                );
            }

            if (!item.getStatus().equals("PRESENT")
                    && !item.getStatus().equals("ABSENT")) {

                throw new RuntimeException(
                        "Invalid attendance status"
                );
            }

            AttendanceRecord record =
                    recordRepository
                            .findBySessionIdAndStudentId(
                                    session.getId(),
                                    student.getId()
                            )
                            .orElse(null);

            if (record == null) {

                record =
                        new AttendanceRecord();

                record.setSession(session);
                record.setStudent(student);
            }

            record.setStatus(
                    item.getStatus()
            );

            recordRepository.save(record);
        }
    }

    public List<AttendanceHistoryResponse>
    studentHistory(String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        Student student =
                studentRepository
                        .findByUserId(user.getId())
                        .orElseThrow();

        return recordRepository
                .findByStudentId(student.getId())
                .stream()
                .map(record ->
                        new AttendanceHistoryResponse(
                                record.getId(),
                                record.getSession()
                                        .getAttendanceDate(),
                                record.getSession()
                                        .getSubject()
                                        .getName(),
                                record.getSession()
                                        .getSection()
                                        .getName(),
                                record.getSession()
                                        .getPeriodNumber(),
                                record.getStatus()
                        )
                )
                .toList();
    }
}

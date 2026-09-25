package com.smartAttendence.repository;

import com.smartAttendence.entity.FacultySubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultySubjectRepository
        extends JpaRepository<FacultySubject, Long> {

    List<FacultySubject> findByFacultyId(Long facultyId);

    boolean existsByFacultyIdAndSubjectIdAndSectionId(
            Long facultyId,
            Long subjectId,
            Long sectionId
    );
}

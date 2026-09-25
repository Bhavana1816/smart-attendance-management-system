package com.smartAttendence.repository;

import com.smartAttendence.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    List<Student> findBySectionId(Long sectionId);

    Optional<Student> findByUserId(Long userId);
}
package com.smartAttendence.repository;

import com.smartAttendence.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<Departments, Long> {
}
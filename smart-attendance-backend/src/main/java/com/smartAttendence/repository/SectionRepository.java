package com.smartAttendence.repository;

import com.smartAttendence.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository
        extends JpaRepository<Section, Long> {
}

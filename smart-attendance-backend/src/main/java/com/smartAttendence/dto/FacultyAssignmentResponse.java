package com.smartAttendence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FacultyAssignmentResponse {

    private Long subjectId;
    private String subjectCode;
    private String subjectName;

    private Long sectionId;
    private String sectionName;
    private Integer semester;
}
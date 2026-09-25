package com.smartAttendence.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "faculty_subjects",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "faculty_id",
                                "subject_id",
                                "section_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultySubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
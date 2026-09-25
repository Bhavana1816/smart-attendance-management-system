package com.smartAttendence.entity;

import com.smartAttendence.entity.Departments;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer semester;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Departments department;
}
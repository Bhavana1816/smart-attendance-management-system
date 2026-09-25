package com.smartAttendence.entity;

import com.smartAttendence.entity.Section;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String registerNumber;

    private String name;

    private String email;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
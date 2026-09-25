package com.smartAttendence.entity;


import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Table(name = "departments")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Departments {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column(unique = true)
        private String code;
    }


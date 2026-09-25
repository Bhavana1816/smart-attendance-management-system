package com.smartAttendence;

import com.smartAttendence.entity.*;
import com.smartAttendence.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class SmartAttendanceDataInitializer {

    @Bean
    CommandLineRunner initializeData(
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            SectionRepository sectionRepository,
            StudentRepository studentRepository,
            FacultyRepository facultyRepository,
            SubjectRepository subjectRepository,
            FacultySubjectRepository facultySubjectRepository,
            AttendanceSessionRepository sessionRepository,
            AttendanceRecordRepository recordRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            if (userRepository.count() > 0) {
                return;
            }

            // Department

            Departments department =
                    new Departments();

            department.setName(
                    "Computer Science and Engineering"
            );

            department.setCode("CSE");

            departmentRepository.save(
                    department
            );

            // Section

            Section section =
                    new Section();

            section.setName("A");
            section.setSemester(6);
            section.setDepartment(
                    department
            );

            sectionRepository.save(section);

            // Subject

            Subject subject =
                    new Subject();

            subject.setCode("CS601");
            subject.setName(
                    "Java Programming"
            );
            subject.setDepartment(
                    department
            );

            subjectRepository.save(subject);

            // Admin

            User admin =
                    new User();

            admin.setName("Admin");
            admin.setEmail(
                    "admin@college.com"
            );
            admin.setPassword(
                    passwordEncoder.encode(
                            "Admin@123"
                    )
            );
            admin.setRole("ADMIN");
            admin.setCreatedAt(
                    LocalDateTime.now()
            );

            userRepository.save(admin);

            // Faculty user

            User facultyUser =
                    new User();

            facultyUser.setName(
                    "John Faculty"
            );
            facultyUser.setEmail(
                    "faculty@college.com"
            );
            facultyUser.setPassword(
                    passwordEncoder.encode(
                            "Faculty@123"
                    )
            );
            facultyUser.setRole("FACULTY");
            facultyUser.setCreatedAt(
                    LocalDateTime.now()
            );

            userRepository.save(
                    facultyUser
            );

            // Faculty

            Faculty faculty =
                    new Faculty();

            faculty.setUser(
                    facultyUser
            );
            faculty.setEmployeeNumber(
                    "FAC001"
            );
            faculty.setName(
                    "John Faculty"
            );
            faculty.setEmail(
                    "faculty@college.com"
            );
            faculty.setDepartment(
                    department
            );

            facultyRepository.save(
                    faculty
            );

            // Faculty assignment

            FacultySubject mapping =
                    new FacultySubject();

            mapping.setFaculty(faculty);
            mapping.setSubject(subject);
            mapping.setSection(section);

            facultySubjectRepository.save(
                    mapping
            );

            // Student user

            User studentUser =
                    new User();

            studentUser.setName(
                    "Bhavana Student"
            );
            studentUser.setEmail(
                    "student@college.com"
            );
            studentUser.setPassword(
                    passwordEncoder.encode(
                            "Student@123"
                    )
            );
            studentUser.setRole(
                    "STUDENT"
            );
            studentUser.setCreatedAt(
                    LocalDateTime.now()
            );

            userRepository.save(
                    studentUser
            );

            // Student

            Student student =
                    new Student();

            student.setUser(
                    studentUser
            );
            student.setRegisterNumber(
                    "CSE001"
            );
            student.setName(
                    "Bhavana Student"
            );
            student.setEmail(
                    "student@college.com"
            );
            student.setSection(
                    section
            );

            studentRepository.save(
                    student
            );

            // Attendance session

            AttendanceSession session =
                    new AttendanceSession();

            session.setSubject(subject);
            session.setSection(section);
            session.setFaculty(faculty);
            session.setAttendanceDate(
                    LocalDate.now()
            );
            session.setPeriodNumber(1);
            session.setCreatedAt(
                    LocalDateTime.now()
            );

            sessionRepository.save(
                    session
            );

            // Attendance

            AttendanceRecord record =
                    new AttendanceRecord();

            record.setSession(session);
            record.setStudent(student);
            record.setStatus(
                    "PRESENT"
            );

            recordRepository.save(
                    record
            );

            System.out.println(
                    "=============================="
            );

            System.out.println(
                    "Sample users created"
            );

            System.out.println(
                    "Admin: admin@college.com / Admin@123"
            );

            System.out.println(
                    "Faculty: faculty@college.com / Faculty@123"
            );

            System.out.println(
                    "Student: student@college.com / Student@123"
            );

            System.out.println(
                    "=============================="
            );
        };
    }
}
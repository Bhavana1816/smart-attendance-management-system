package com.smartAttendence.Controller;

import com.smartAttendence.entity.Student;
import com.smartAttendence.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Student not found")
                );
    }

    @GetMapping("/section/{sectionId}")
    public List<Student> getBySection(
            @PathVariable Long sectionId) {

        return studentRepository.findBySectionId(sectionId);
    }
}
package com.example.web.service; // Ensure this package matches your project structure

import com.example.web.models.Student;
import com.example.web.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student saveStudent(Student student) {
        // Business logic or validation can go here
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id){


        studentRepository.deleteById(id);

    }
    public Student getStudentById(Long id){

        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }

    // You would add findById, delete, and update methods here as well.
}
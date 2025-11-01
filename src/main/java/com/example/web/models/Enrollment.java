package com.example.web.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentid; // enrollmentid pk

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID", nullable = false)
    private Student student; // StudentID (FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "YearID", nullable = false)
    private AcademicYear academicYear; // YearID (FK)

    private String gradeLevel; // GradeLevel
    private String section; // Section
    private LocalDate enrollmentDate; // EnrollmentDate
    // ... other fields and standard getters/setters
    // --- Relationship Getter and Setter (Where setStudent() MUST be) ---

    // Getter for the Student relationship
    public Student getStudent() {
        return student;
    }

    /**
     * Setter for the Student relationship. This method is essential
     * for JPA to manage the foreign key column in the database.
     */
    public void setStudent(Student student) {
        this.student = student;
    }
}

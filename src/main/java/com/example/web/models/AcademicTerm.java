package com.example.web.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "academic_term")
public class AcademicTerm {

    // Primary Key for the term itself (auto-generated Long)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Term-specific columns
    private String name; // e.g., "Semester 1", "Quarter 3", "Term 1"
    private LocalDate startDate;
    private LocalDate endDate;

    // Many-to-One Relationship back to AcademicYear
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id") // Foreign key column name in the database
    private AcademicYear academicYear;

    // Contructors
    public AcademicTerm() {
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }
}
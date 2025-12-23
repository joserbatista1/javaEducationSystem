package com.example.web.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@Where(clause = "is_current = true") // Example soft-delete clause
@Table(name = "academic_year")
public class AcademicYear {

    // Primary Key: The year string (e.g., "2025-2026")
    @Id
    private String id;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrent;

    // One-to-Many Relationship with AcademicTerm
    // 'mappedBy' indicates the field in the AcademicTerm entity that owns the relationship.
    // CascadeType.ALL ensures terms are saved/deleted with the year.
    @OneToMany(mappedBy = "academicYear", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcademicTerm> terms = new ArrayList<>();

    // Contructors (using modern date types)
    public AcademicYear() {
    }

    // --- Getters and Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public List<AcademicTerm> getTerms() {
        return terms;
    }

    public void setTerms(List<AcademicTerm> terms) {
        this.terms = terms;
    }

    // Helper method to manage the bidirectional relationship
    public void addTerm(AcademicTerm term) {
        terms.add(term);
        term.setAcademicYear(this);
    }
}
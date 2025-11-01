package com.example.web.models;

import jakarta.persistence.*; // Use jakarta.persistence for Spring Boot 3+
import jakarta.persistence.Table;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.*;

/**
 * JPA Entity and Model class for a Teacher.
 * Configured for persistence with an auto-incremented primary key.
 */
@Entity
@Where(clause = "status = true")
@Table(name = "teachers") // Changed table name to plural 'teachers'


//@NamedNativeQuery(
//        name = "safeTeacherDelete",
//        query = "UPDATE  Teachers set status =false where id=:id",
//        resultClass = Subject.class
//)

@SQLDelete(sql = "UPDATE teachers set status=false where id=?")
@SQLSelect(sql = "SELECT * FROM teachers")


public class Teacher {

    // --- Primary Key Configuration ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY ensures auto-increment in MySQL/PostgreSQL
    private Long id;

    // --- Basic Teacher Information ---
    private String name;
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentDate;

    // --- Educational Fields (from the HTML form) ---
    // Note: For List fields, JPA typically requires separate tables or custom converters.
    // We'll use a simple String/VARCHAR to store the selected value for simplicity.
    // For sections, we'll use a simple String, assuming the HTML multiple select
    // will be converted to a comma-separated string, or we'd need a @ElementCollection.

    @ElementCollection(fetch = FetchType.EAGER) // Stores the list of sections in a separate table linked to Teacher
    @CollectionTable(name = "teacher_sections", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "section")
    private Set<String> sections = new HashSet<>();


    @ElementCollection(fetch = FetchType.EAGER) // Stores the list of sections in a separate table linked to Teacher
    @CollectionTable(name = "teacher_grades", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "grades")
    private Set<Integer> grades= new HashSet<>() ;

    @ElementCollection(fetch = FetchType.EAGER) // Stores the list of sections in a separate table linked to Teacher
    @CollectionTable(name = "teacher_levels", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "levels")
    private Set<String> levels = new HashSet<>();
    private Boolean status = true; // Default status

    // --- Constructors ---

    /**
     * Mandatory default no-argument constructor for Spring/JPA/Thymeleaf.
     */
    public Teacher() {
    }

    // --- Getters and Setters (REQUIRED for JPA and Thymeleaf) ---

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    // Since the form had a single select for grade, using Integer is standard.
    // If you intended the database to store a list, you'd need @ElementCollection here too.
    public Set<Integer> getGrades() {
        return grades;
    }

    public void setGrades(Set<Integer> grades) {
        this.grades = grades;
    }

    // Getter and Setter for the List of Sections
    public Set<String> getSections() {
        return sections;
    }

    public void setSections(Set<String> sections) {
        this.sections = sections;
    }

    public Set<String> getLevels() {
        return levels;
    }

    public void setLevels(Set<String> level) {
        this.levels = level;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    // --- Optional: toString, equals, and hashCode for better debugging/testing ---

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", grade=" + grades +
                ", sections=" + sections +
                ", level='" + levels + '\'' +
                '}';
    }


}

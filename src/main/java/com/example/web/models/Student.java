package com.example.web.models;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity and Model class for a Student.
 * The fields match the inputs defined in the Thymeleaf template.
 */
@Entity
@Where(clause = "status = true")
@Table(name = "students")

@SQLDelete(sql = "UPDATE students set status=false where id=?")
public class Student {

    // Primary Key Configuration for JPA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Form fields
    private String name;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentDate;
    private String grade;
    private String section;
    private String level;
    private Boolean status= true;
    @OneToMany(mappedBy = "student", // 'student' is the field in the Enrollment entity
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments = new HashSet<>();

    public Set<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(Set<Enrollment> enrollments) { this.enrollments = enrollments; }


    // --- **Crucial Helper Methods for Bidirectional Relationship** ---
    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setStudent(this); // Synch the 'many' side
    }

    public void removeEnrollment(Enrollment enrollment) {
        this.enrollments.remove(enrollment);
        enrollment.setStudent(null); // Unlink the 'many' side
    }
    // --- Constructors ---

    /**
     * Mandatory default no-argument constructor for Spring/JPA/Thymeleaf.
     */
    public Student() {
    }

    // --- Getters and Setters ---
    // These are required for Thymeleaf's th:field="*{...}" to read and write properties.

    public Long getId() {
        return id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status){
        this.status = status;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    // Optional: toString() for logging/debugging
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", grade='" + grade + '\'' +
                ", section='" + section + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
package com.example.web.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "status = true")
@Table(name = "subjects")
@SQLDelete(sql = "UPDATE subjects set status=false where id=?")

public class Subject {
    public enum Type {
        TECHNICAL,NONTECHNICAL;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Subject name is required.")
    private String name;

    @NotBlank(message = "Course code is required.")
    @Column(unique = true, nullable = false)
    private String code;

    @NotNull(message = "Type is required.")
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull(message = "Credits are required.")
    @Min(value = 1, message = "Credits must be at least 1.")
    @Column(nullable = false)
    private Integer credits;

    // FIX: Changed from @ElementCollection to @ManyToMany for proper DB linkage
    @ManyToMany(fetch = FetchType.LAZY) // Use LAZY to prevent N+1 issues in large lists
    @JoinTable(
            name = "subject_teachers",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> teachers = new ArrayList<>(); // Now holds actual Teacher entities

    @Lob
    private String description;

    private Boolean status = true;

    // --- Mandatory Constructor ---
    public Subject() {}

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    // NOTE: This now returns List<Teacher>
    public List<Teacher> getTeachers() { return teachers; }
    public void setTeachers(List<Teacher> teachers) { this.teachers = teachers; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
}
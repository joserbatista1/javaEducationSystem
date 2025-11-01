package com.example.web.dtos;

import java.util.List;

/**
 * DTO/ViewModel for displaying Subject data, including mapped Instructor names.
 */
public class SubjectViewModel {

    private final Long id;
    private final String code;
    private final String name;
    private final Integer credits;
    private final String description;
    private final List<String> instructorNames;

    // Constructor that accepts all necessary display data
    public SubjectViewModel(
            Long id,
            String code,
            String name,
            Integer credits,
            String description,
            List<String> instructorNames) {

        this.id = id;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.instructorNames = instructorNames;
    }

    // --- Getters ---
    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public Integer getCredits() { return credits; }
    public String getDescription() { return description; }
    public List<String> getInstructorNames() { return instructorNames; }
}
package com.example.web.service;

import com.example.web.models.Teacher;
import com.example.web.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional; // Important for findById

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // --- CRUD Operations ---

    /**
     * Retrieves all Teacher entities from the database.
     * @return A list of all teachers.
     */
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAllWithCollections();
    }

    /**
     * Saves a new teacher or updates an existing one.
     * @param teacher The Teacher object to save or update.
     * @return The saved/updated Teacher object.
     */
    @Transactional
    public Teacher saveTeacher(Teacher teacher) {
        // Business logic or validation can go here
        return teacherRepository.save(teacher);
    }

    /**
     * Retrieves a teacher by their ID.
     * Uses Optional to handle cases where the ID does not exist.
     * @param id The ID of the teacher to find.
     * @return An Optional containing the Teacher if found, or empty otherwise.
     */
    public Teacher getTeacherById(Long id) {
        System.out.println("this is working!");
        return teacherRepository.findByIdWithCollections(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + id));
        // Replace RuntimeException with a custom exception (e.g., ResourceNotFoundException)
    }
    /**
     * Deletes a teacher entity based on its ID.
     * @param id The ID of the teacher to delete.
     */
    public void deleteTeacher(Long id) {
        // Call the custom soft delete method
        teacherRepository.softDeleteById(id);
        // Note: No need for try-catch or findById, as the update query handles missing IDs gracefully.
    }

    // You can add more complex update logic here if needed,
    // but the generic saveTeacher method usually handles it fine.
}
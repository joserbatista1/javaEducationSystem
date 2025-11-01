package com.example.web.repositories;

import com.example.web.models.Teacher;
//import com.example.web.models.Student; // Adjust package name
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Replace 'Long' with the data type of your Student ID/Primary Key
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // Spring Data JPA automatically provides:
    // save(), findAll(), findById(), deleteById(), existsById(), etc.

    // You can add custom queries here if needed, e.g.:
    // List<Student> findByGrade(String grade);
    // 1. Fetch ALL teachers with their collections
    // The JPQL 'FETCH' keyword ensures all collections are loaded in one go.
    @Query("SELECT DISTINCT t FROM Teacher t " +
            "LEFT JOIN FETCH t.sections " +
            "LEFT JOIN FETCH t.grades " +
            "LEFT JOIN FETCH t.levels")
    List<Teacher> findAllWithCollections();

    // 2. Fetch ONE teacher with their collections (for the edit page)
    @Query("SELECT t FROM Teacher t " +
            "LEFT JOIN FETCH t.sections " +
            "LEFT JOIN FETCH t.grades " +
            "LEFT JOIN FETCH t.levels " +
            "WHERE t.id = :id")
    Optional<Teacher> findByIdWithCollections(Long id);

    @Modifying // Indicates that this query modifies the data
    @Transactional // Ensures the operation runs within a transaction
    @Query("UPDATE Teacher t SET t.status = false WHERE t.id = :id")
    void softDeleteById(Long id);
}
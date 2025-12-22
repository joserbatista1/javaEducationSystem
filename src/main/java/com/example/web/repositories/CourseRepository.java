package com.example.web.repositories;


import com.example.web.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository takes two parameters:
 * 1. The Entity class (Course)
 * 2. The type of the ID field (Long)
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // You can add custom query methods here if needed, for example:
    // List<Course> findByNameContaining(String name);
}
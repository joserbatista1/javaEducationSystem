package com.example.web.repositories;


import com.example.web.models.Student; // Adjust package name
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Replace 'Long' with the data type of your Student ID/Primary Key
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    // Spring Data JPA automatically provides:
    // save(), findAll(), findById(), deleteById(), existsById(), etc.

    // You can add custom queries here if needed, e.g.:
    // List<Student> findByGrade(String grade);
}
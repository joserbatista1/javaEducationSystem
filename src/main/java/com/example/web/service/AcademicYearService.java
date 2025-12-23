package com.example.web.service;

import com.example.web.models.AcademicYear;
import com.example.web.models.Course;
import com.example.web.repositories.AcademicYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcademicYearService {

    private final AcademicYearRepository academicYearRepository;


    @Autowired
    public AcademicYearService( AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    public List<AcademicYear> getAllAcademicYears() {
        return academicYearRepository.findAll();
    }

    public Optional<AcademicYear> getAcademicYearById(String id) {
        return academicYearRepository.findById(id);
    }

    public AcademicYear createacademicYear(AcademicYear academicYear) {
        return academicYearRepository.save(academicYear);
    }

    public void deleteAcademicYear(String id) {
        academicYearRepository.deleteById(id);
    }

}
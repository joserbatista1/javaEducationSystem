package com.example.web.service;

import com.example.web.models.Subject;
import com.example.web.models.Teacher;
import com.example.web.repositories.SubjectRepository;
import com.example.web.dtos.SubjectViewModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    // TeacherService is no longer needed here for mapping, but we'll keep it simple
    // If you remove it, adjust the constructor.

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + id));
    }

    // Primary method to fetch and map data for the list view
    public List<SubjectViewModel> getAllSubjectViewModels() {
        // Find all subjects. Hibernate will efficiently load related teachers when needed.
        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
    // Helper method to transform Subject entity to display DTO
    private SubjectViewModel mapToViewModel(Subject subject) {

        // Map the loaded Teacher entities directly to formatted names
        List<String> names = subject.getTeachers().stream()
                // Format name as "Firstname Lastname"
                .map(t -> t.getName() + " " + t.getLastName())
                .collect(Collectors.toList());

        // Create and return the ViewModel
        return new SubjectViewModel(
                subject.getId(),
                subject.getCode(),
                subject.getName(),
                subject.getCredits(),
                subject.getDescription(),
                names
        );
    }
}
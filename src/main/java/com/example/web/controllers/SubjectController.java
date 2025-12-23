//package com.example.web.controllers;
//import com.example.web.models.Teacher;
//import com.example.web.service.SubjectService;
//import com.example.web.models.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import jakarta.validation.Valid;
//import com.example.web.service.TeacherService;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Controller
//public class SubjectController {
//
//    private final SubjectService subjectService;
//    private final TeacherService teacherService;
//
//    @Autowired
//    public SubjectController(SubjectService subjectService, TeacherService teacherService) {
//        this.subjectService = subjectService;
//        this.teacherService = teacherService;
//    }
//
//    @GetMapping("/subjects")
//    public String listSubjects(
//            Model model,
//            @RequestParam(required = false) String keyword,
//            @RequestParam(defaultValue = "1") int page) {
//
//        // CHANGE: Call the service method that returns the View Models (with names)
//        model.addAttribute("subjects", subjectService.getAllSubjectViewModels());
//
//        // Pagination and Search data (simulated)
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", 5);
//
//        return "subjects";
//    }
//
//    @GetMapping("/subjects/new")
//    public String showNewSubjectForm(Model model) {
//
//        model.addAttribute("subject", new Subject());
//
//        // Pass the list of available Teacher entities for the dropdown
//        model.addAttribute("availableInstructors", teacherService.getAllTeachers());
//
//        return "subjects_new";
//    }
//
//    @PostMapping("/subjects")
//    public String saveSubject(
//            @Valid @ModelAttribute("subject") Subject subject,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes,
//            Model model)
//    {
//        // 1. Handle Validation Errors
//        if (bindingResult.hasErrors()) {
//            // CRITICAL: Re-populate instructors so the dropdown is not empty upon error
//            model.addAttribute("availableInstructors", teacherService.getAllTeachers());
//
//            model.addAttribute("errorMessage", "There are errors in the form. Please correct them.");
//            return "subjects_new";
//        }
//
//        // 2. Successful Submission
//        subjectService.saveSubject(subject);
//
//        redirectAttributes.addFlashAttribute("successMessage", "Subject '" + subject.getName() + "' successfully created!");
//        return "redirect:/subjects";
//    }
//    @GetMapping("/subjects/edit/{id}")
//    public String showEditSubjectForm(@PathVariable Long id, Model model) {
//
//        // 1. Fetch the existing Subject
//        Subject subject = subjectService.getSubjectById(id);
//        model.addAttribute("subject", subject);
//
//        // 2. Fetch all available instructors for the multi-select box
//        List<Teacher> availableInstructors = teacherService.getAllTeachers();
//        model.addAttribute("availableInstructors", availableInstructors);
//
//        return "editSubject"; // Renders src/main/resources/templates/editSubject.html
//    }
//    @PostMapping("/subjects/edit/{id}")
//    public String updateSubject(
//            @PathVariable Long id,
//            @ModelAttribute("subject") Subject formSubject) {
//
//        // 1. Fetch the EXISTING Subject entity from the database
//        Subject existingSubject = subjectService.getSubjectById(id);
//
//        // 2. Manually update simple fields
//        existingSubject.setName(formSubject.getName());
//        existingSubject.setCode(formSubject.getCode());
//        existingSubject.setCredits(formSubject.getCredits());
//        existingSubject.setStatus(formSubject.getStatus());
//        existingSubject.setDescription(formSubject.getDescription());
//        System.out.println("WORKS HERE!");
//        // 4. Update the managed Many-to-Many collection
//        existingSubject.getTeachers().clear();
//        existingSubject.getTeachers().addAll(formSubject.getTeachers());
//
//        // 5. Save the managed entity
//        subjectService.saveSubject(existingSubject);
//
//        return "redirect:/subjects";
//    }
//
//}
package com.example.web.controllers;

import com.example.web.models.Subject;
import com.example.web.models.Teacher;
import com.example.web.service.SubjectService;
import com.example.web.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder; // New Import
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller

public class SubjectController {

    private final SubjectService subjectService;
    private final TeacherService teacherService;

    public SubjectController(SubjectService subjectService, TeacherService teacherService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    // ⭐ CRITICAL FIX: Tell Spring to ignore the field during automatic binding
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // The field name in the Subject model that holds the Teacher collection
        // Based on the error, the property in your Subject class is 'teachers'.
        binder.setDisallowedFields("teachers");

        // If your property is named 'instructors', use:
        // binder.setDisallowedFields("instructors");
    }

    @GetMapping("/subjects")
    public String listSubjects(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page) {

        // CHANGE: Call the service method that returns the View Models (with names)
        model.addAttribute("subjects", subjectService.getAllSubjectViewModels());

        // Pagination and Search data (simulated)
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 5);

        return "subjects";
    }

    @GetMapping("/subjects/new")
    public String showNewSubjectForm(Model model) {

        model.addAttribute("subject", new Subject());

        // Pass the list of available Teacher entities for the dropdown
        model.addAttribute("availableInstructors", teacherService.getAllTeachers());
        model.addAttribute("availableTypes", List.of("TECHNICAL", "NONTECHNICAL"));
        return "subjects_new";
    }

    @PostMapping("/subjects")
    public String saveSubject(
            @Valid @ModelAttribute("subject") Subject subject,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model)
    {
        // 1. Handle Validation Errors
        if (bindingResult.hasErrors()) {
            // CRITICAL: Re-populate instructors so the dropdown is not empty upon error
            model.addAttribute("availableInstructors", teacherService.getAllTeachers());

            model.addAttribute("errorMessage", "There are errors in the form. Please correct them.");
            return "subjects_new";
        }

        // 2. Successful Submission
        subjectService.saveSubject(subject);

        redirectAttributes.addFlashAttribute("successMessage", "Subject '" + subject.getName() + "' successfully created!");
        return "redirect:/subjects";
    }
    @GetMapping("/subjects/edit/{id}")
    public String showEditSubjectForm(@PathVariable Long id, Model model) {
        // ... (unchanged logic) ...
        Subject subject = subjectService.getSubjectById(id);
        model.addAttribute("subject", subject);
        List<Teacher> availableInstructors = teacherService.getAllTeachers();
        model.addAttribute("availableInstructors", availableInstructors);
        return "editSubject";
    }

    //----------------------------------------------------------------------

    /**
     * PUT handler now receives the basic fields via @ModelAttribute and the
     * IDs separately via @RequestParam for manual conversion.
     */
    @PostMapping("/subjects/edit/{id}")
    public String updateSubject(
            @PathVariable Long id,
            // Spring now ignores the 'teachers' field on this object
            @ModelAttribute("subject") Subject formSubject,
            // Capture the raw IDs separately from the form field named 'teachers'
            @RequestParam(value = "teachers", required = false) List<String> teacherIds) {

        // NOTE: If your HTML <select> uses th:field="*{instructors}", change the @RequestParam name to "instructors"
        // @RequestParam(value = "instructors", required = false) List<String> instructorIds)

        // 1. Fetch the EXISTING Subject entity
        Subject existingSubject = subjectService.getSubjectById(id);

        // 2. Manually update simple fields (these were bound successfully by @ModelAttribute)
        existingSubject.setName(formSubject.getName());
//        existingSubject.setCode(formSubject.getCode());
        existingSubject.setCredits(formSubject.getCredits());
        existingSubject.setStatus(formSubject.getStatus());
        existingSubject.setDescription(formSubject.getDescription());

        // 3. Manually convert IDs to Teacher entities
        Set<Teacher> updatedTeachers = new HashSet<>();
        if (teacherIds != null) {
            for (String teacherIdStr : teacherIds) {
                try {
                    Long teacherId = Long.valueOf(teacherIdStr);
                    Teacher teacher = teacherService.getTeacherById(teacherId);
                    updatedTeachers.add(teacher);
                } catch (NumberFormatException  e) {
                    // Log or handle this error
                }
            }
        }

        // 4. Update the Many-to-Many collection using the corrected list of entities
        existingSubject.getTeachers().clear(); // Use getTeachers() or getInstructors() based on your model
        existingSubject.getTeachers().addAll(updatedTeachers);

        // 5. Save and redirect
        subjectService.saveSubject(existingSubject);

        return "redirect:/subjects";
    }
    @GetMapping("/subjects/delete/{id}")
    public String deleteTeacher(@PathVariable Long id){

        subjectService.deleteSubject(id);

        return "redirect:/subjects";
    }
}
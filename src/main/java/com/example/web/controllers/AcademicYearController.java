package com.example.web.controllers;

import com.example.web.models.AcademicYear;
import com.example.web.service.AcademicYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/academicyear")
public class AcademicYearController {

    private final AcademicYearService academicService;

    @Autowired
    public AcademicYearController(AcademicYearService academicService) {
        this.academicService = academicService;
    }

    // List all courses
    @GetMapping
    public String viewAcademicYearList(Model model) {
        // 'model' is used to pass data to the view
        model.addAttribute("listYears", academicService.getAllAcademicYears());
        return "years_index"; //
    }
//
    // Show create form
    @GetMapping("/showNewAcademicYearForm")
    public String showNewAcademicYearForm(Model model) {
        AcademicYear academicYear = new AcademicYear();
        model.addAttribute("", academicYear);
        return "new_academicYear"; // Looks for new_course.html
    }
//
//    // Save course to database
//    @PostMapping("/saveCourse")
//    public String saveCourse(@ModelAttribute("course") Course course) {
//        courseService.createCourse(course);
//        return "redirect:/courses"; // Redirects to the list view
//    }
}
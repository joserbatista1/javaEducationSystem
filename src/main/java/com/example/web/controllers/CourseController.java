package com.example.web.controllers;

import com.example.web.models.Course;
import com.example.web.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // List all courses
    @GetMapping
    public String viewCourseList(Model model) {
        // 'model' is used to pass data to the view
        model.addAttribute("listCourses", courseService.getAllCourses());
        return "course_index"; // Looks for src/main/resources/templates/course_index.html
    }

    // Show create form
    @GetMapping("/showNewCourseForm")
    public String showNewCourseForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "new_course"; // Looks for new_course.html
    }

//    // Save course to database
//    @PostMapping("/saveCourse")
//    public String saveCourse(@ModelAttribute("course") Course course) {
//        courseService.save(course);
//        return "redirect:/courses"; // Redirects to the list view
//    }
}
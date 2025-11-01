package com.example.web.controllers;
import com.example.web.models.Student;
import com.example.web.models.Teacher;
import com.example.web.service.StudentService;
import com.example.web.service.TeacherService;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TeacherController {

    private final TeacherService teacherService;
    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teachers")
    public String listStudents(
            Model model) {

        // --- 1. Model Population (Simulated Data) ---
        model.addAttribute("teachers", teacherService.getAllTeachers());

        // NOTE: Real-world search/pagination would be implemented in the service
        // using JpaRepository methods. For simplicity, we just fetch all here.

        // Pagination and Search data (Placeholder logic remains, but needs service implementation)


        return "teachers"; // Renders the students.html template
    }
//    @GetMapping("/teachers/new")
//    public String saveStudent(Model model) {
//
//        Teacher teacher = new Teacher();
//        teacher.setEnrollmentDate(LocalDate.now());
//        // 2. Redirect the user after a successful save
//        // Redirecting back to the list page is a common practice.
//        model.addAttribute("teacher", teacher);
//
//        return "newTeacher";
//    }
@GetMapping("/teachers/new")
public String showNewTeacherForm(Model model) {
    // Create an empty Teacher object to bind form data
    Teacher teacher = new Teacher();
    // Set the enrollment date for the form object (it's a hidden field)
    teacher.setEnrollmentDate(LocalDate.now());
    model.addAttribute("teacher", teacher);
    return "newTeacher"; // Refers to src/main/resources/templates/new-teacher-form.html
}

@PostMapping("/teachers/save")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher){

  Teacher savedTeacher=teacherService.saveTeacher(teacher);

  return "redirect:/teachers";
    }
@GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable Long id){

        teacherService.deleteTeacher(id);

        return "redirect:/teachers";
}
    @GetMapping("/teachers/edit/{id}")
    public String editTeacherForm(@PathVariable Long id, Model model) {

        // 1. Get the Teacher object (using your fixed service method)
        Teacher teacher = teacherService.getTeacherById(id);
        System.out.println(teacher.getName());
        model.addAttribute("teacher", teacher);
        // TEMPLATE USES: ${grades}
        model.addAttribute("Allgrades", List.of(1, 2, 3, 4, 5, 6));

        // TEMPLATE USES: ${levels}
        model.addAttribute("Alllevels", List.of("Primary","Secondary"));

        // TEMPLATE USES: ${sections}
        model.addAttribute("Allsections", List.of("A", "B", "C", "D", "E"));
        // 2. Provide the lists of available options for the selects
        // (These lists can come from config, an enum, or another service

        return "editTeacher"; // Matches the template file name
    }

    @PostMapping("/teachers/edit/{id}")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute("teacher") Teacher formTeacher) {

        // 1. Fetch the EXISTING Teacher entity from the database
        //    (The service should use findById().orElseThrow(...) for safety)
        Teacher existingTeacher = teacherService.getTeacherById(id);

        // 2. MANUALLY MERGE the fields from the formTeacher onto the existingTeacher
        //    We must ensure we merge collections correctly.

        existingTeacher.setName(formTeacher.getName());
        existingTeacher.setLastName(formTeacher.getLastName());
        existingTeacher.setStatus(formTeacher.getStatus());

        // Handle Collections: Clear existing collections and add the new ones from the form.
        // This is the CRITICAL part that prevents the NoSuchElementException on merge.

        existingTeacher.getGrades().clear();
        existingTeacher.getGrades().addAll(formTeacher.getGrades());

        existingTeacher.getLevels().clear();
        existingTeacher.getLevels().addAll(formTeacher.getLevels());

        existingTeacher.getSections().clear();
        existingTeacher.getSections().addAll(formTeacher.getSections());

        // The existingTeacher object is now updated.
        // 3. Save the managed entity.
        teacherService.saveTeacher(existingTeacher);

        return "redirect:/teachers";
    }

}
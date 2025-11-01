//package com.example.web;
//import com.example.web.models.Student;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//@Controller
//public class StudentController {
//    LocalTime myObj = LocalTime.now();
//    @GetMapping("/students")
//    public String listStudents(
//            Model model,
//            @RequestParam(required = false) String keyword,
//            @RequestParam(defaultValue = "1") int page) {
//
//        // --- 1. Model Population (Simulated Data) ---
//
//        // Pagination and Search data
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", 5); // Example total pages
//
//        return "students"; // Renders the students.html template
//    }
//    @GetMapping("/students/new")
//    public String showNewStudentForm(Model model) {
//
//        Student student = new Student();
//
//        // 2. OPTIONAL: Set default values, like the current enrollment date.
//        // This is what makes the date field in the template pre-filled.
//        student.setEnrollmentDate(LocalDate.now());
//        // 3. Add the object to the Model with the key "student".
//        // This key MUST match th:object="${student}" in your HTML template.
//        model.addAttribute("student", student);
//
//        // 4. Return the template name (assuming your file is named newStudent.html)
//        return "newStudent";
//    }
//    @PostMapping("/students/save")
//    // @ModelAttribute("student") binds the form data to the Student object.
//    // NOTE: You can add @Valid and BindingResult here for validation (commented out for simplicity).
//    public String saveStudent(@ModelAttribute("student") Student student) {
//
//        // The 'student' object now contains all the data submitted by the form!
//        System.out.println("Student Name: " + student.getName() + " " + student.getLastName());
//        System.out.println("Enrolled on: " + student.getEnrollmentDate());
//        System.out.println("Grade/Section/Level: " + student.getGrade() + "/" + student.getSection() + "/" + student.getLevel());
//        System.out.println(student.toString());
//        // 1. Call your service layer to persist the data (e.g., save to a database)
//        // studentService.save(student);
//
//        // 2. Redirect the user after a successful save (Post/Redirect/Get pattern)
//        // This prevents double submission if the user refreshes the page.
//        return "redirect:/students";
//    }
//}
package com.example.web.controllers;

import com.example.web.models.Student;
import com.example.web.models.Teacher;
import com.example.web.service.StudentService; // Import the service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.text.WordUtils;
import java.time.LocalDate;
import java.util.List;
// Removed unused 'LocalTime' import

@Controller
public class StudentController {

    // Inject the service using constructor injection (best practice)
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Note: The 'myObj' LocalTime field was removed as it was unused and non-standard.

    @GetMapping("/students")
    public String listStudents(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page) {

        // --- ADAPTED: Use the service to get real data ---

        // 1. Get ALL students from the database (or a paginated list if implemented)
        model.addAttribute("students", studentService.getAllStudents());

        // NOTE: Real-world search/pagination would be implemented in the service
        // using JpaRepository methods. For simplicity, we just fetch all here.

        // Pagination and Search data (Placeholder logic remains, but needs service implementation)
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 1); // Reset to 1 page unless you implement pagination

        return "students"; // Renders the students.html template
    }

    // --- Method to display the form remains mostly the same ---
    @GetMapping("/students/new")
    public String showNewStudentForm(Model model) {
        Student student = new Student();

        // Pre-fill with current date
        student.setEnrollmentDate(LocalDate.now());

        model.addAttribute("student", student);
        return "newStudent";
    }

    // --- Method to save the data is fully adapted ---
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute("student") Student student) {

        student.setName(WordUtils.capitalize(student.getName()));
        // 1. ADAPTED: Call your service layer to persist the data to the DB
        Student savedStudent = studentService.saveStudent(student);

        // (Optional: Log the saved object to verify the ID was generated)
        System.out.println("Saved Student ID: " + savedStudent.getId());

        // 2. Redirect the user after a successful save
        // Redirecting back to the list page is a common practice.
        return "redirect:/students";
    }
    @PostMapping("/students/delete/{id}")

    public String deleteStudent(@PathVariable Long id) {


        studentService.deleteStudent(id);
        // 2. Redirect the user after a successful save
        // Redirecting back to the list page is a common practice.
        return "redirect:/students";
    }

//    @PostMapping("/students/delete/keyword={id}")
//
//    public String search(@PathVariable Long id) {
//
//
//        studentService.deleteStudent(id);
//        // 2. Redirect the user after a successful save
//        // Redirecting back to the list page is a common practice.
//        return "redirect:/students";
//    }
//    @GetMapping("/students/edit/{id}")
//    public String editStudent(@PathVariable Long id,Model model){
//        // 1. Get the Teacher object (using your fixed service method)
//        Student student = studentService.getStudentById(id);
//        System.out.println(student.getName());
//        model.addAttribute("teacher", student);
//        // TEMPLATE USES: ${grades}
//        model.addAttribute("Allgrades", List.of(1, 2, 3, 4, 5, 6));
//
//        // TEMPLATE USES: ${levels}
//        model.addAttribute("Alllevels", List.of("Primary","Secondary"));
//
//        // TEMPLATE USES: ${sections}
//        model.addAttribute("Allsections", List.of("A", "B", "C", "D", "E"));
//        // 2. Provide the lists of available options for the selects
//        // (These lists can come from config, an enum, or another service
//        return "editStudent";
//    }
@GetMapping("/students/edit/{id}")
public String showEditStudentForm(@PathVariable Long id, Model model) {

    // 1. Fetch the existing student
    // This method should use findById().orElseThrow(...) in the service.
    Student student = studentService.getStudentById(id);

    model.addAttribute("student", student);

    // 2. Add lists for the dropdown menus (Select fields)
    // NOTE: Adjust these lists to match your specific domain values
    model.addAttribute("Allgrades", List.of(1, 2, 3, 4, 5, 6));
    model.addAttribute("Alllevels", List.of("Elementary", "Primary", "Secondary"));
    model.addAttribute("Allsections", List.of("A", "B", "C", "D"));

    return "editStudent"; // Renders src/main/resources/templates/editStudent.html
}
    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student formStudent) {

        // 1. Fetch the EXISTING Student entity from the database
        // This is the robust method to handle updates with JPA/Hibernate.
        Student existingStudent = studentService.getStudentById(id);

        // 2. Manually merge/update fields from the form object onto the managed entity
        existingStudent.setName(formStudent.getName());
        existingStudent.setLastName(formStudent.getLastName());
        existingStudent.setGrade(formStudent.getGrade());
        existingStudent.setLevel(formStudent.getLevel());
        existingStudent.setSection(formStudent.getSection());
        existingStudent.setStatus(formStudent.getStatus());

        // Enrollment Date is typically immutable, so we don't update it here.

        // 3. Save the managed entity (save() performs an UPDATE because ID is present)
        studentService.saveStudent(existingStudent);

        // 4. Redirect to the student directory
        return "redirect:/students";
    }
}
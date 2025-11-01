package com.example.web;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// @SpringBootApplication
// @RestController
// public class WebApplication {
//    public static void main(String[] args) {
//      SpringApplication.run(WebApplication.class, args);
//    }
//    @GetMapping("/hello")
//    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//      return String.format("Hello %s!", name);
//    }
   
//    @GetMapping("/goodbye")
//    public String goodbye(@RequestParam(value = "name", defaultValue = "World") String name) {
//      return String.format("Bye %s!", name);
//    }
// }
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.boot.autoconfigure.SpringBootApplication; // Imported
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// Removed unused import: import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication // <--- ADD THIS ANNOTATION
@Controller
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
    @GetMapping({ "/home"})
    public String index(Model model) {
        return "login"; // Renders the index.html template
    }
    
//    @GetMapping("/login")
//    public String getHtmlPage(@RequestParam(value = "error", defaultValue = "null") String name, Model model) {
//        // Add data to the Model that the template can use
//        model.addAttribute("message","Hello "+ name);
//
//
//        //
//        return "login";
//    }

    @GetMapping({"/main"})
    public String mainPage(Model model) {
        return "index"; // Renders the index.html template
    }
    
    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {

        // --- 1. KPI Data ---
        model.addAttribute("dashboardTitle", "Academic Administrator Dashboard");
        model.addAttribute("totalStudents", 150); // gradeService.countAllStudents()
        model.addAttribute("averageGPA", 3.25);   // gradeService.calculateOverallGPA()
        model.addAttribute("totalSubjects", 18);  // subjectService.countAllSubjects()

        // --- 2. Chart Data ---
        // A Map<String, Integer> of grade letter and the count of students who received it
        Map<String, Integer> gradeDistribution = Map.of(
                "A", 30,
                "B", 50,
                "C", 40,
                "D", 15,
                "F", 10
        );
        model.addAttribute("gradeDistribution", gradeDistribution);

        // --- 3. Table Data (Alerts) ---
        // A list of objects representing students who are currently struggling
        List<Alert> lowGradeAlerts = List.of(
                new Alert(1001, "Jane Doe", "Calculus II", 58.2),
                new Alert(1005, "Michael Scott", "Ethics 101", 59.5)
                // ... more alerts
        );
        model.addAttribute("lowGradeAlerts", lowGradeAlerts);

        return "Dashboard"; // Renders the dashboard.html template
    }

    // Simple inner class for the table data model
    public static class Alert {
        public final int studentId;
        public final String studentName;
        public final String subjectName;
        public final double grade;

        public Alert(int studentId, String studentName, String subjectName, double grade) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.subjectName = subjectName;
            this.grade = grade;
        }
    }

//    @GetMapping("/subjects")
//    public String getSubjects(@RequestParam(value = "error", defaultValue = "null") String name, Model model) {
//        // Add data to the Model that the template can use
//        model.addAttribute("message","Hello "+ name);
//
//
//        // Return the name of the template file (welcome.html)
//        return "subjects";
//    }

}
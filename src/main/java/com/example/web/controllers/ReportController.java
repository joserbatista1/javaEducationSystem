package com.example.web.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ReportController {

    @GetMapping("/reports")
    public String viewReports(
            Model model,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) String term,
            @RequestParam(required = false, defaultValue = "0.0") Double minGpa
    ) {
        // --- 1. Pass Current Filters to Model ---
        model.addAttribute("currentSubjectId", subjectId);
        model.addAttribute("currentTerm", term);
        model.addAttribute("minGpa", minGpa);

        // --- 2. Filter Options (Used for Select Boxes) ---
        model.addAttribute("allSubjects", List.of(
                new SubjectOption(1L, "Calculus I"),
                new SubjectOption(2L, "Data Structures")
        ));
        model.addAttribute("availableTerms", List.of("Fall 2024", "Spring 2025", "Summer 2025"));

        // --- 3. Summary Statistics (KPIs) ---
        // This object would be returned by your service layer after applying filters
        SummaryStats stats = new SummaryStats(95, 78.4, 85, 14);
        model.addAttribute("summaryStats", stats);

        // --- 4. Chart Data ---
        // Chart 1: Grade Distribution
        Map<String, Integer> gradeDistribution = Map.of(
                "A", 20,
                "B", 35,
                "C", 30,
                "D", 10,
                "F", 5
        );
        model.addAttribute("gradeDistributionChartData", gradeDistribution);

        // Chart 2: Performance Over Time (for Line Chart)
        PerformanceData performanceTimeline = new PerformanceData(
                List.of("Fall 2023", "Spring 2024", "Fall 2024"),
                List.of(75.2, 81.5, 79.8)
        );
        model.addAttribute("performanceTimelineChartData", performanceTimeline);

        return "reports"; // Renders the reports.html template
    }

    // Simple records/POJOs for data transfer:
    public record SubjectOption(Long id, String name) {}
    public record SummaryStats(int totalStudents, double averageGrade, int passingRate, int failingStudents) {}
    public record PerformanceData(List<String> labels, List<Double> data) {}
}
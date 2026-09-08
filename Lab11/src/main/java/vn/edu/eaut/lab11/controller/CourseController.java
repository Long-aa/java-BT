package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab11.model.Course;

import java.util.List;

@Controller
public class CourseController {

    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = List.of(
            new Course("IT3242", "Công nghệ Java", 3),
            new Course("IT1110", "Tin học đại cương", 4),
            new Course("IT3100", "Lập trình hướng đối tượng", 3),
            new Course("IT3230", "Lập trình C", 3),
            new Course("IT4100", "Quản trị dự án CNTT", 3)
        );
        model.addAttribute("courses", courses);
        return "courses";
    }
}

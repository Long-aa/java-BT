package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hệ thống quản lý sinh viên");
        model.addAttribute("message", "Chào mừng đến với Spring Boot");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("course", "Công nghệ Java");
        model.addAttribute("chapter", "Chương 4 - Spring Framework");
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("department", "Khoa Công nghệ thông tin");
        model.addAttribute("address", "Toà nhà EAUT, Hà Nội");
        model.addAttribute("phone", "0123-456-789");
        return "contact";
    }
}

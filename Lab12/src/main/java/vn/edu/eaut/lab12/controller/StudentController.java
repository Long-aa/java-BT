package vn.edu.eaut.lab12.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

import java.util.List;
import java.util.Optional;

/**
 * Controller điều hướng và xử lý các yêu cầu liên quan đến sinh viên:
 * - Bài 3: Hiển thị danh sách sinh viên (/students)
 * - Bài 4: Form thêm sinh viên (/students/create)
 * - Bài 5: Xử lý lưu và validation (@Valid, BindingResult)
 * - Bài 6: Xem chi tiết sinh viên (/students/detail/{id})
 * - Bài 7: Sửa thông tin sinh viên (/students/edit/{id})
 * - Bài 8: Xóa sinh viên (/students/delete/{id})
 * - Bài 9: Tìm kiếm theo họ tên (?keyword=...)
 * - Bài 10: Validation kiểm tra mã sinh viên không được trùng
 */
@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Bài 3 & Bài 9: Hiển thị danh sách sinh viên & Tìm kiếm theo họ tên
     */
    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Student> students;
        if (keyword != null && !keyword.trim().isEmpty()) {
            students = studentService.searchByName(keyword);
            model.addAttribute("keyword", keyword.trim());
        } else {
            students = studentService.findAll();
        }
        model.addAttribute("students", students);
        return "students/list";
    }

    /**
     * Bài 4: Tạo form thêm sinh viên
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Thêm mới sinh viên");
        model.addAttribute("isEdit", false);
        return "students/form";
    }

    /**
     * Bài 4, Bài 5 & Bài 10: Xử lý lưu sinh viên kèm Validation & Check trùng mã SV
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        // Bài 10: Kiểm tra mã sinh viên không trùng trong danh sách hiện có
        if (student.getStudentCode() != null && !student.getStudentCode().trim().isEmpty()) {
            if (studentService.existsByStudentCode(student.getStudentCode(), student.getId())) {
                result.rejectValue("studentCode", "duplicate",
                        "Mã sinh viên '" + student.getStudentCode().trim() + "' đã tồn tại trong hệ thống! Vui lòng nhập mã khác.");
            }
        }

        // Bài 5: Nếu có lỗi validation, trả về form cùng thông báo lỗi
        if (result.hasErrors()) {
            boolean isEdit = student.getId() != null;
            model.addAttribute("pageTitle", isEdit ? "Cập nhật sinh viên" : "Thêm mới sinh viên");
            model.addAttribute("isEdit", isEdit);
            return "students/form";
        }

        boolean isEdit = student.getId() != null;
        studentService.save(student);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Cập nhật thông tin sinh viên [" + student.getFullName() + "] thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Thêm mới sinh viên [" + student.getFullName() + "] thành công!");
        }

        return "redirect:/students";
    }

    /**
     * Bài 6: Xem chi tiết sinh viên theo ID
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên có ID = " + id);
            return "redirect:/students";
        }
        model.addAttribute("student", studentOpt.get());
        return "students/detail";
    }

    /**
     * Bài 7: Viết chức năng sửa thông tin sinh viên
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên có ID = " + id + " để chỉnh sửa!");
            return "redirect:/students";
        }
        model.addAttribute("student", studentOpt.get());
        model.addAttribute("pageTitle", "Cập nhật sinh viên");
        model.addAttribute("isEdit", true);
        return "students/form";
    }

    /**
     * Bài 8: Viết chức năng xóa sinh viên khỏi danh sách
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isPresent()) {
            String name = studentOpt.get().getFullName();
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa sinh viên [" + name + "] khỏi hệ thống!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên có ID = " + id + " để xóa!");
        }
        return "redirect:/students";
    }
}

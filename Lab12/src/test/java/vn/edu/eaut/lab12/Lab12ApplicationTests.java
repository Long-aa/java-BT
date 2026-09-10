package vn.edu.eaut.lab12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import vn.edu.eaut.lab12.controller.StudentController;
import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Lab12ApplicationTests {

    private StudentService studentService;
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
        studentService.initSampleData();
        studentController = new StudentController(studentService);
    }

    /**
     * Kiểm tra context load thành công
     */
    @Test
    void contextLoads() {
        assertNotNull(studentService);
        assertNotNull(studentController);
    }

    /**
     * Test Bài 1: Tạo model Student với đầy đủ constructor, getter, setter
     */
    @Test
    void testStudentModel() {
        Student s = new Student();
        s.setId(99L);
        s.setStudentCode("SV9999");
        s.setFullName("Nguyễn Văn Test");
        s.setEmail("test@eaut.edu.vn");
        s.setClassName("CNTT-K14");

        assertEquals(99L, s.getId());
        assertEquals("SV9999", s.getStudentCode());
        assertEquals("Nguyễn Văn Test", s.getFullName());
        assertEquals("test@eaut.edu.vn", s.getEmail());
        assertEquals("CNTT-K14", s.getClassName());
    }

    /**
     * Test Bài 2: Tạo service giả lập dữ liệu bằng List trong bộ nhớ
     */
    @Test
    void testStudentServiceCRUD() {
        List<Student> list = studentService.findAll();
        assertFalse(list.isEmpty());
        int initialSize = list.size();

        // Thêm mới
        Student newStudent = new Student("SV1000", "Lê Văn Mới", "moi.lv@eaut.edu.vn", "CNTT-K14");
        studentService.save(newStudent);
        assertNotNull(newStudent.getId());
        assertEquals(initialSize + 1, studentService.findAll().size());

        // Tìm theo ID (Bài 6)
        Optional<Student> found = studentService.findById(newStudent.getId());
        assertTrue(found.isPresent());
        assertEquals("Lê Văn Mới", found.get().getFullName());

        // Cập nhật thông tin (Bài 7)
        found.get().setFullName("Lê Văn Cập Nhật");
        studentService.save(found.get());
        Optional<Student> updated = studentService.findById(newStudent.getId());
        assertTrue(updated.isPresent());
        assertEquals("Lê Văn Cập Nhật", updated.get().getFullName());

        // Xóa sinh viên (Bài 8)
        boolean deleted = studentService.deleteById(newStudent.getId());
        assertTrue(deleted);
        assertEquals(initialSize, studentService.findAll().size());
    }

    /**
     * Test Bài 3: Controller hiển thị danh sách sinh viên (/students)
     */
    @Test
    void testControllerList() {
        Model model = new ConcurrentModel();
        String view = studentController.list(null, model);
        assertEquals("students/list", view);
        assertTrue(model.containsAttribute("students"));
        List<?> students = (List<?>) model.getAttribute("students");
        assertNotNull(students);
        assertFalse(students.isEmpty());
    }

    /**
     * Test Bài 4: Controller form thêm sinh viên (/students/create)
     */
    @Test
    void testControllerCreateForm() {
        Model model = new ConcurrentModel();
        String view = studentController.createForm(model);
        assertEquals("students/form", view);
        assertTrue(model.containsAttribute("student"));
        assertEquals(false, model.getAttribute("isEdit"));
    }

    /**
     * Test Bài 5: Controller lưu dữ liệu và validation
     */
    @Test
    void testControllerSaveValidationErrors() {
        Student invalidStudent = new Student(); // tất cả các trường để trống
        BindingResult result = new BeanPropertyBindingResult(invalidStudent, "student");
        result.rejectValue("studentCode", "NotBlank", "Mã sinh viên không được để trống");
        Model model = new ConcurrentModel();
        RedirectAttributes ra = new RedirectAttributesModelMap();

        String view = studentController.save(invalidStudent, result, model, ra);
        assertEquals("students/form", view);
        assertTrue(result.hasErrors());
    }

    /**
     * Test Bài 6: Controller xem chi tiết sinh viên (/students/detail/{id})
     */
    @Test
    void testControllerDetail() {
        Model model = new ConcurrentModel();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        String view = studentController.detail(1L, model, ra);
        assertEquals("students/detail", view);
        assertTrue(model.containsAttribute("student"));
    }

    /**
     * Test Bài 7: Controller form sửa thông tin sinh viên (/students/edit/{id})
     */
    @Test
    void testControllerEditForm() {
        Model model = new ConcurrentModel();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        String view = studentController.editForm(1L, model, ra);
        assertEquals("students/form", view);
        assertTrue(model.containsAttribute("student"));
        assertEquals(true, model.getAttribute("isEdit"));
    }

    /**
     * Test Bài 8: Controller xóa sinh viên khỏi danh sách (/students/delete/{id})
     */
    @Test
    void testControllerDelete() {
        RedirectAttributes ra = new RedirectAttributesModelMap();
        int beforeSize = studentService.findAll().size();
        String view = studentController.delete(1L, ra);
        assertEquals("redirect:/students", view);
        assertEquals(beforeSize - 1, studentService.findAll().size());
        assertTrue(ra.getFlashAttributes().containsKey("successMessage"));
    }

    /**
     * Test Bài 9: Controller tìm kiếm sinh viên theo họ tên
     */
    @Test
    void testControllerSearchByName() {
        Model model = new ConcurrentModel();
        String view = studentController.list("An", model);
        assertEquals("students/list", view);
        assertTrue(model.containsAttribute("students"));
        List<Student> results = (List<Student>) model.getAttribute("students");
        assertNotNull(results);
        for (Student s : results) {
            assertTrue(s.getFullName().toLowerCase().contains("an"));
        }
    }

    /**
     * Test Bài 10: Validation kiểm tra mã sinh viên không trùng trong danh sách hiện có
     */
    @Test
    void testControllerDuplicateStudentCodeValidation() {
        // Sinh viên mới nhưng mã SV0001 đã tồn tại trong danh sách mẫu
        Student duplicateStudent = new Student("SV0001", "Trùng Mã", "trung@eaut.edu.vn", "CNTT-K14");
        BindingResult result = new BeanPropertyBindingResult(duplicateStudent, "student");
        Model model = new ConcurrentModel();
        RedirectAttributes ra = new RedirectAttributesModelMap();

        String view = studentController.save(duplicateStudent, result, model, ra);
        assertEquals("students/form", view);
        assertTrue(result.hasFieldErrors("studentCode"));
        assertEquals("duplicate", result.getFieldError("studentCode").getCode());

        // Ngược lại, khi cập nhật chính sinh viên có ID = 1 với mã của chính mình thì KHÔNG báo lỗi trùng
        Student selfUpdateStudent = new Student(1L, "SV0001", "Nguyễn Văn An Cập Nhật", "an.nv@eaut.edu.vn", "CNTT-K14");
        BindingResult selfResult = new BeanPropertyBindingResult(selfUpdateStudent, "student");
        String updateView = studentController.save(selfUpdateStudent, selfResult, model, ra);
        assertEquals("redirect:/students", updateView);
        assertFalse(selfResult.hasFieldErrors("studentCode"));
    }
}

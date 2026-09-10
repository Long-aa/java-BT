package vn.edu.eaut.lab12.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import vn.edu.eaut.lab12.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Bài 2: Tạo service giả lập dữ liệu lưu bằng List trong bộ nhớ
 * Hỗ trợ các chức năng: Tìm kiếm (Bài 9), Chi tiết (Bài 6), Cập nhật (Bài 7), Xóa (Bài 8),
 * Kiểm tra trùng mã sinh viên (Bài 10).
 */
@Service
public class StudentService {
    private final List<Student> students = new ArrayList<>();
    private long nextId = 1;

    @PostConstruct
    public void initSampleData() {
        // Khởi tạo dữ liệu mẫu ban đầu để giao diện sinh động và dễ kiểm thử
        save(new Student("SV0001", "Nguyễn Văn An", "an.nv@eaut.edu.vn", "CNTT-K14"));
        save(new Student("SV0002", "Trần Thị Bình", "binh.tt@eaut.edu.vn", "CNTT-K14"));
        save(new Student("SV0003", "Lê Hoàng Nam", "nam.lh@eaut.edu.vn", "DPT-K14"));
        save(new Student("SV0004", "Phạm Minh Thảo", "thao.pm@eaut.edu.vn", "ATTT-K14"));
        save(new Student("SV0005", "Vũ Quốc Cường", "cuong.vq@eaut.edu.vn", "CNTT-K14"));
    }

    /**
     * Lấy toàn bộ danh sách sinh viên
     */
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    /**
     * Bài 6: Tìm sinh viên theo ID
     */
    public Optional<Student> findById(Long id) {
        if (id == null) return Optional.empty();
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    /**
     * Bài 2 & Bài 7: Lưu thông tin sinh viên (Thêm mới nếu id == null, Cập nhật nếu id != null)
     */
    public void save(Student student) {
        if (student == null) return;

        if (student.getId() == null) {
            // Thêm mới
            student.setId(nextId++);
            students.add(student);
        } else {
            // Cập nhật sinh viên đã tồn tại
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId().equals(student.getId())) {
                    students.set(i, student);
                    return;
                }
            }
            // Nếu không tìm thấy id thì thêm như mới
            students.add(student);
        }
    }

    /**
     * Bài 8: Xóa sinh viên khỏi danh sách theo ID
     */
    public boolean deleteById(Long id) {
        if (id == null) return false;
        return students.removeIf(s -> s.getId().equals(id));
    }

    /**
     * Bài 9: Tìm kiếm sinh viên theo họ tên (không phân biệt hoa thường)
     */
    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String cleanKeyword = keyword.trim().toLowerCase();
        return students.stream()
                .filter(s -> s.getFullName() != null && s.getFullName().toLowerCase().contains(cleanKeyword))
                .collect(Collectors.toList());
    }

    /**
     * Bài 10: Kiểm tra mã sinh viên đã tồn tại hay chưa
     * @param studentCode Mã sinh viên cần kiểm tra
     * @param excludeId ID sinh viên bỏ qua (dùng khi cập nhật, không tính bản ghi hiện tại)
     * @return true nếu trùng, false nếu hợp lệ
     */
    public boolean existsByStudentCode(String studentCode, Long excludeId) {
        if (studentCode == null || studentCode.trim().isEmpty()) {
            return false;
        }
        String codeTrimmed = studentCode.trim();
        return students.stream().anyMatch(s ->
                s.getStudentCode() != null &&
                s.getStudentCode().trim().equalsIgnoreCase(codeTrimmed) &&
                (excludeId == null || !s.getId().equals(excludeId))
        );
    }
}

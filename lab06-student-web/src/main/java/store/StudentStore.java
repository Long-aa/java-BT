package store;

import model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentStore {
    private static final List<Student> students = new ArrayList<>();

    public static List<Student> findAll() {
        return students;
    }

    public static Student findById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static void add(Student student) {
        students.add(student);
    }

    public static void update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                return;
            }
        }
    }

    public static void delete(String id) {
        students.removeIf(s -> s.getId().equals(id));
    }

    public static int getTotalStudents() {
        return students.size();
    }
}

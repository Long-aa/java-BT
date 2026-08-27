package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Student;
import store.StudentStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                StudentStore.delete(id);
            }
            response.sendRedirect(request.getContextPath() + "/students");
            return;
        } else if ("edit".equals(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                Student student = StudentStore.findById(id);
                request.setAttribute("student", student);
            }
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        String searchQuery = request.getParameter("searchName");
        List<Student> allStudents = StudentStore.findAll();
        List<Student> filteredStudents = new ArrayList<>();
        
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            for (Student s : allStudents) {
                if (s.getName() != null && s.getName().toLowerCase().contains(searchQuery.toLowerCase().trim())) {
                    filteredStudents.add(s);
                }
            }
        } else {
            filteredStudents = allStudents;
        }

        request.setAttribute("students", filteredStudents);
        request.setAttribute("searchQuery", searchQuery);
        request.getRequestDispatcher("/student-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String className = request.getParameter("className");
        String email = request.getParameter("email");

        Student student = new Student(id, name, className, email);
        
        if ("update".equals(action)) {
            StudentStore.update(student);
        } else {
            StudentStore.add(student);
        }

        response.sendRedirect(request.getContextPath() + "/students");
    }
}

package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Student;
import store.StudentStore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Student> allStudents = StudentStore.findAll();
        
        int totalStudents = allStudents.size();
        
        Map<String, Integer> classStats = new HashMap<>();
        for (Student s : allStudents) {
            String className = s.getClassName();
            if (className != null && !className.isEmpty()) {
                classStats.put(className, classStats.getOrDefault(className, 0) + 1);
            }
        }
        
        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("classStats", classStats);
        
        request.getRequestDispatcher("/welcome.jsp").forward(request, response);
    }
}

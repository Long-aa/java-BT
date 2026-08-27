package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import model.Student;
import store.StudentStore;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Ung dung Lab 6 da khoi dong. Dang khoi tao du lieu...");
        StudentStore.add(new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@example.com"));
        StudentStore.add(new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com"));
        StudentStore.add(new Student("SV003", "Nong Quoc Dung", "DCCNTT12", "dung@example.com"));
        StudentStore.add(new Student("SV004", "Tran Thi Lan", "DCCNTT12", "lan@example.com"));
        StudentStore.add(new Student("SV005", "Le Van Cuong", "DCCNTT12", "cuong@example.com"));
        StudentStore.add(new Student("SV006", "Truong Van Long", "DCCNTT12", "long@example.com"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Ung dung Lab 6 da dung. Tong so sinh vien con lai: " + StudentStore.getTotalStudents());
    }
}

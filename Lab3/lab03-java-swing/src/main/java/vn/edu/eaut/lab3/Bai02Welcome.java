package vn.edu.eaut.lab3;

import javax.swing.*;

public class Bai02Welcome extends JFrame {

    public Bai02Welcome() {
        setTitle("Welcome");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bai02Welcome frame = new Bai02Welcome();
            frame.setVisible(true);
            
            JOptionPane.showMessageDialog(frame, "Welcome to Java Swing", "Message", JOptionPane.INFORMATION_MESSAGE);
            
            System.exit(0);
        });
    }
}

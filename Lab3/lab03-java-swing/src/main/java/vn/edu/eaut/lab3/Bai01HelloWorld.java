package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai01HelloWorld extends JFrame {

    public Bai01HelloWorld() {
        setTitle("My First Swing App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Hello World !", SwingConstants.LEFT);
        add(label, BorderLayout.BEFORE_FIRST_LINE);
        label.setForeground(Color.BLUE);
        label.setFont(new Font("Arial", Font.BOLD, 24));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai01HelloWorld().setVisible(true));
    }
}

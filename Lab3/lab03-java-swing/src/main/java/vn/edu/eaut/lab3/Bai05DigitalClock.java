package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bai05DigitalClock {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Digital Clock");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            JLabel timeLabel = new JLabel(formattedDateTime, SwingConstants.CENTER);
            timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
            frame.add(timeLabel);

            frame.setVisible(true);
        });
    }
}

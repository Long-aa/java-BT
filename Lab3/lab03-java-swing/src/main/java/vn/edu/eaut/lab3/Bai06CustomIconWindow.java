package vn.edu.eaut.lab3;

import javax.swing.*;

public class Bai06CustomIconWindow {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom Icon Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);

            ImageIcon icon = new ImageIcon("logo.png");
            if (icon.getImageLoadStatus() == java.awt.MediaTracker.ERRORED) {
                System.out.println("Note: Could not load logo.png from working directory. Using default icon.");
            } else {
                frame.setIconImage(icon.getImage());
            }

            JLabel label = new JLabel("Custom Icon Window", SwingConstants.CENTER);
            frame.add(label);

            frame.setVisible(true);
        });
    }
}

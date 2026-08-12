package vn.edu.eaut.lab3;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Bai04ImageViewer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Tạo JFrame với tiêu đề "Image Viewer"
            JFrame frame = new JFrame("Image Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Thêm JLabel hiển thị một hình ảnh từ file (sử dụng ImageIcon)
            // Lưu ý: Thay đổi "sample.jpg" thành đường dẫn tới file ảnh thực tế của bạn
            ImageIcon imageIcon = new ImageIcon("sample.jpg");
            JLabel imageLabel = new JLabel(imageIcon);
            
            // Nếu không tìm thấy ảnh, có thể hiển thị text thay thế
            if (imageIcon.getIconWidth() == -1) {
                imageLabel.setText("Không tìm thấy hình ảnh 'sample.jpg'");
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
            }
            
            frame.add(imageLabel);
            
            // Đặt kích thước JFrame tự động theo kích thước hình ảnh
            frame.pack();
            
            // Hiển thị ở giữa màn hình
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

import java.util.Scanner;

public class Baitap4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập cạnh a: ");
        double a = scanner.nextDouble();
        System.out.print("Nhập cạnh b: ");
        double b = scanner.nextDouble();
        System.out.print("Nhập cạnh c: ");
        double c = scanner.nextDouble();
        
        if (a > 0 && b > 0 && c > 0 && (a + b > c) && (a + c > b) && (b + c > a)) {
            System.out.print("Đây là 3 cạnh của một tam giác. Loại tam giác: ");
            if (a == b && b == c) {
                System.out.println("Tam giác đều.");
            } else if (a == b || b == c || a == c) {
                if (a * a + b * b == c * c || a * a + c * c == b * b || b * b + c * c == a * a) {
                    System.out.println("Tam giác vuông cân.");
                } else {
                    System.out.println("Tam giác cân.");
                }
            } else if (a * a + b * b == c * c || a * a + c * c == b * b || b * b + c * c == a * a) {
                System.out.println("Tam giác vuông.");
            } else {
                System.out.println("Tam giác thường.");
            }
        } else {
            System.out.println("Ba số này không tạo thành một tam giác.");
        }
        scanner.close();
    }
}

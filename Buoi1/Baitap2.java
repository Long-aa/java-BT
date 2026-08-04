import java.util.Scanner;

public class Baitap2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số nguyên dương n: ");
        int n = scanner.nextInt();
        
        if (n <= 0) {
            System.out.println("Vui lòng nhập số nguyên dương.");
        } else {
            double sum = 0.0;
            for (int i = 1; i <= n; i++) {
                sum += 1.0 / i;
            }
            System.out.println("Tổng nghịch đảo s = 1 + 1/2 + ... + 1/" + n + " là: " + sum);
        }
        scanner.close();
    }
}

import java.util.Scanner;

public class Baitap1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số nguyên dương n: ");
        int n = scanner.nextInt();
        
        if (n <= 0) {
            System.out.println("Vui lòng nhập số nguyên dương.");
        } else {
            int sum = 0;
            for (int i = 2; i <= n; i += 2) {
                sum += i;
            }
            System.out.println("Tổng các số chẵn từ 2 đến " + n + " là: " + sum);
        }
        scanner.close();
    }
}

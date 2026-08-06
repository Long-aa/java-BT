import java.util.Scanner;

public class Baitap2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap so nguyen duong n: ");
        int n = scanner.nextInt();
        
        if (n <= 0) {
            System.out.println("Vui long nhap so nguyen duong.");
        } else {
            double sum = 0.0;
            for (int i = 1; i <= n; i++) {
                sum += 1.0 / i;
            }
            System.out.println("TTong nghich dao s = 1 + 1/2 + ... + 1/" + n + " la: " + sum);
        }
        scanner.close();
    }
}

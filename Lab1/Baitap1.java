import java.util.Scanner;

public class Baitap1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap so nguyen duong n: ");
        int n = scanner.nextInt();
        
        if (n <= 0) {
            System.out.println("Vui long nhap so nguyen duong.");
        } else {
            int sum = 0;
            for (int i = 2; i <= n; i += 2) {
                sum += i;
            }
            System.out.println("TTong cac so chan tu 2 den " + n + " la: " + sum);
        }
        scanner.close();
    }
}

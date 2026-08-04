import java.util.Scanner;

public class Baitap5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số nguyên dương n: ");
        int n = scanner.nextInt();
        
        if (n <= 0) {
            System.out.println("Vui lòng nhập số nguyên dương.");
        } else {
            System.out.print(n + " số Fibonacci đầu tiên: ");
            long f0 = 0, f1 = 1, fn;
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    System.out.print(f0 + " ");
                } else if (i == 1) {
                    System.out.print(f1 + " ");
                } else {
                    fn = f0 + f1;
                    System.out.print(fn + " ");
                    f0 = f1;
                    f1 = fn;
                }
            }
            System.out.println();
        }
        scanner.close();
    }
}

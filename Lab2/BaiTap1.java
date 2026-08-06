import java.util.Scanner;

public class BaiTap1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap diem chuyen can: ");
        double cc = sc.nextDouble();

        System.out.print("Nhap diem giua ky: ");
        double gk = sc.nextDouble();

        System.out.print("Nhap diem cuoi ky: ");
        double ck = sc.nextDouble();

        double tong = cc * 0.1 + gk * 0.3 + ck * 0.6;

        System.out.printf("Diem tong ket = %.2f", tong);
    }
}
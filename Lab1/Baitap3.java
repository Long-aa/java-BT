import java.util.Scanner;

public class Baitap3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        int i;
        for (i = 2; i < n; i++)
            if (n % i == 0) break;

        if (n >= 2 && i == n)
            System.out.println("La so nguyen to");
        else
            System.out.println("Khong phai so nguyen to");
    }
}
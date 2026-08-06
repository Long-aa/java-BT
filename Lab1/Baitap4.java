import java.util.Scanner;

public class Baitap4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap a b c: ");
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();

        if (a + b <= c || a + c <= b || b + c <= a)
            System.out.println("Khong phai tam giac");
        else if (a == b && b == c)
            System.out.println("Tam giac deu");
        else if (a == b || a == c || b == c)
            System.out.println("Tam giac can");
        else if (a*a + b*b == c*c || a*a + c*c == b*b || b*b + c*c == a*a)
            if (a == b || a == c || b == c  ) System.out.println("Tam giac vuong can");
            else System.out.println("Tam giac vuong");
        else
            System.out.println("Tam giac thuong");
    }
}
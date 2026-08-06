package QLTT;

import java.util.Scanner;

public class Nguoi {
    protected String hoTen;
    protected int tuoi;
    protected String queQuan;

    public Nguoi() {}

    public Nguoi(String hoTen, int tuoi, String queQuan) {
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.queQuan = queQuan;
    }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public int getTuoi() { return tuoi; }
    public void setTuoi(int tuoi) { this.tuoi = tuoi; }

    public String getQueQuan() { return queQuan; }
    public void setQueQuan(String queQuan) { this.queQuan = queQuan; }

    public void nhapThongTin(Scanner scanner) {
        System.out.print("Nhap ho ten: ");
        hoTen = scanner.nextLine();
        System.out.print("Nhap tuoi: ");
        tuoi = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhap que quan: ");
        queQuan = scanner.nextLine();
    }

    public void hienThiThongTin() {
        System.out.printf("Ho ten: %s | Tuoi: %d | Que quan: %s%n", hoTen, tuoi, queQuan);
    }
}

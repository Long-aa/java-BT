package QLTT;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private List<CBGV> danhSachCBGV = new ArrayList<>();

    public void themCBGV(CBGV cbgv) {
        danhSachCBGV.add(cbgv);
        System.out.println("Da them can bo giang vien!");
    }

    public void xoaCBGV(String maSoGV) {
        if (danhSachCBGV.removeIf(gv -> gv.getMaSoGV().equals(maSoGV))) {
            System.out.println("Da xoa giang vien ma " + maSoGV);
        } else {
            System.out.println("Khong tim thay ma " + maSoGV);
        }
    }

    public void hienThiDanhSach() {
        if (danhSachCBGV.isEmpty()) {
            System.out.println("Danh sach trong!");
        } else {
            System.out.println("--- Danh sach ---");
            danhSachCBGV.forEach(CBGV::hienThiThongTin);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main ql = new Main();
        
        while (true) {
            System.out.println("\n--- QUAN LY CBGV ---");
            System.out.println("1. Them\n2. Xoa\n3. Hien thi\n0. Thoat");
            System.out.print("Chon: ");
            
            switch (scanner.nextLine()) {
                case "1" -> {
                    CBGV gv = new CBGV();
                    try {
                        gv.nhapThongTin(scanner);
                        ql.themCBGV(gv);
                    } catch (Exception e) {
                        System.out.println("Nhap sai dinh dang!");
                    }
                }
                case "2" -> {
                    System.out.print("Nhap ma GV can xoa: ");
                    ql.xoaCBGV(scanner.nextLine());
                }
                case "3" -> ql.hienThiDanhSach();
                case "0" -> {
                    System.out.println("Ket thuc.");
                    return;
                }
                default -> System.out.println("Vui long chon lai!");
            }
        }
    }
}

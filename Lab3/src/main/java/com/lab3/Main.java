package com.lab3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- CHUONG TRINH QUAN LY SINH VIEN ---");
        
        Student student = new Student();
        
        System.out.println("\n[1] Nhap du lieu sinh vien:");
        student.inputData(scanner);
        
        System.out.println("\n[2] Ket qua:");
        student.displayResult();
        
        scanner.close();
    }
}

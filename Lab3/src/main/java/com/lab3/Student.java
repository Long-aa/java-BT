package com.lab3;

import java.util.Scanner;

public class Student {
    private String id;
    private String name;
    private double attendanceScore;
    private double midtermScore;
    private double finalScore;

    public Student() {
    }

    public void inputData(Scanner scanner) {
        System.out.print("Nhap ma sinh vien: ");
        this.id = scanner.nextLine();
        System.out.print("Nhap ho ten: ");
        this.name = scanner.nextLine();
        
        this.attendanceScore = inputScore(scanner, "Diem chuyen can: ");
        this.midtermScore = inputScore(scanner, "Diem giua ky: ");
        this.finalScore = inputScore(scanner, "Diem cuoi ky: ");
    }

    private double inputScore(Scanner scanner, String message) {
        double score;
        while (true) {
            System.out.print(message);
            try {
                score = Double.parseDouble(scanner.nextLine());
                if (score >= 0 && score <= 10) {
                    break;
                } else {
                    System.out.println("Loi: Diem phai nam trong khoang tu 0 den 10. Vui long nhap lai.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Loi: Dinh dang diem khong hop le. Vui long nhap lai so.");
            }
        }
        return score;
    }

    public double calculateFinalScore() {
        return this.attendanceScore * 0.1 + this.midtermScore * 0.3 + this.finalScore * 0.6;
    }

    public String getGrade() {
        double totalScore = calculateFinalScore();
        if (totalScore >= 8.5) {
            return "A";
        } else if (totalScore >= 7.0) {
            return "B";
        } else if (totalScore >= 5.5) {
            return "C";
        } else if (totalScore >= 4.0) {
            return "D";
        } else {
            return "F";
        }
    }

    public void displayResult() {
        System.out.printf("%s - %s - %.2f - %s\n", this.id, this.name, calculateFinalScore(), getGrade());
    }
}

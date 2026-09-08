package vn.edu.miximoi.model;

/** DTO gộp thông tin Nhân viên + Bảng lương → dùng để hiển thị trên view */
public class PhieuLuong {
    private NhanVien nhanVien;
    private BangLuong bangLuong;

    public PhieuLuong(NhanVien nhanVien, BangLuong bangLuong) {
        this.nhanVien = nhanVien;
        this.bangLuong = bangLuong;
    }

    public NhanVien getNhanVien()   { return nhanVien; }
    public BangLuong getBangLuong() { return bangLuong; }
    public void setNhanVien(NhanVien nv)   { this.nhanVien = nv; }
    public void setBangLuong(BangLuong bl) { this.bangLuong = bl; }
}

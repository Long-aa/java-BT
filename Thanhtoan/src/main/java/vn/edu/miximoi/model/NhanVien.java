package vn.edu.miximoi.model;

public class NhanVien {
    private int id;
    private String maNV;
    private String hoTen;
    private String chucVu;
    private String phongBan;
    private double luongCoBan;    // Lương cơ bản (VNĐ/tháng)
    private double heSoLuong;    // Hệ số lương (1.0 – 5.0)
    private String email;
    private String sdt;

    public NhanVien() {}

    public NhanVien(int id, String maNV, String hoTen, String chucVu, String phongBan,
                    double luongCoBan, double heSoLuong, String email, String sdt) {
        this.id = id; this.maNV = maNV; this.hoTen = hoTen;
        this.chucVu = chucVu; this.phongBan = phongBan;
        this.luongCoBan = luongCoBan; this.heSoLuong = heSoLuong;
        this.email = email; this.sdt = sdt;
    }

    // Getters & Setters
    public int getId()                      { return id; }
    public void setId(int id)               { this.id = id; }
    public String getMaNV()                 { return maNV; }
    public void setMaNV(String maNV)        { this.maNV = maNV; }
    public String getHoTen()                { return hoTen; }
    public void setHoTen(String hoTen)      { this.hoTen = hoTen; }
    public String getChucVu()               { return chucVu; }
    public void setChucVu(String chucVu)    { this.chucVu = chucVu; }
    public String getPhongBan()             { return phongBan; }
    public void setPhongBan(String pb)      { this.phongBan = pb; }
    public double getLuongCoBan()           { return luongCoBan; }
    public void setLuongCoBan(double l)     { this.luongCoBan = l; }
    public double getHeSoLuong()            { return heSoLuong; }
    public void setHeSoLuong(double h)      { this.heSoLuong = h; }
    public String getEmail()                { return email; }
    public void setEmail(String email)      { this.email = email; }
    public String getSdt()                  { return sdt; }
    public void setSdt(String sdt)          { this.sdt = sdt; }

    /** Lương ngạch = luongCoBan * heSoLuong */
    public double getLuongNgach()           { return luongCoBan * heSoLuong; }
}

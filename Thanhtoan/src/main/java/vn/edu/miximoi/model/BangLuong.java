package vn.edu.miximoi.model;

public class BangLuong {
    /** Trạng thái thanh toán */
    public enum TrangThai { CHO_DUYET, DA_DUYET, DA_THANH_TOAN, HUY }

    private int id;
    private int nhanVienId;
    private int thang;
    private int nam;

    // Chấm công
    private int ngayCongChuanThang;  // Số ngày công chuẩn của tháng (thường = 22 hoặc 26)
    private int ngayCongThucTe;       // Số ngày công thực tế
    private int phepNam;              // Số ngày phép năm đã dùng (có lương)
    private int nghiPhep;             // Số ngày nghỉ không lương

    // Lương & phụ cấp
    private double luongNgach;       // = luongCoBan * heSoLuong
    private double phuCapAnCa;
    private double phuCapXang;
    private double phuCapDienThoai;
    private double thuong;           // Thưởng hiệu suất, ngày lễ …

    // Khấu trừ
    private double bhxh;             // 8%
    private double bhyt;             // 1.5%
    private double bhtn;             // 1%
    private double thueTNCN;         // Thuế TNCN ước tính
    private double khauTruKhac;

    private double luongThucLanh;    // = tổng thu nhập - tổng khấu trừ
    private TrangThai trangThai;
    private String ghiChu;
    private String ngayThanhToan;    // ISO date string

    public BangLuong() {
        this.trangThai = TrangThai.CHO_DUYET;
    }

    // ---- Getters & Setters ----
    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }
    public int getNhanVienId()                  { return nhanVienId; }
    public void setNhanVienId(int id)           { this.nhanVienId = id; }
    public int getThang()                       { return thang; }
    public void setThang(int thang)             { this.thang = thang; }
    public int getNam()                         { return nam; }
    public void setNam(int nam)                 { this.nam = nam; }
    public int getNgayCongChuanThang()          { return ngayCongChuanThang; }
    public void setNgayCongChuanThang(int n)    { this.ngayCongChuanThang = n; }
    public int getNgayCongThucTe()              { return ngayCongThucTe; }
    public void setNgayCongThucTe(int n)        { this.ngayCongThucTe = n; }
    public int getPhepNam()                     { return phepNam; }
    public void setPhepNam(int p)               { this.phepNam = p; }
    public int getNghiPhep()                    { return nghiPhep; }
    public void setNghiPhep(int n)              { this.nghiPhep = n; }
    public double getLuongNgach()               { return luongNgach; }
    public void setLuongNgach(double l)         { this.luongNgach = l; }
    public double getPhuCapAnCa()               { return phuCapAnCa; }
    public void setPhuCapAnCa(double p)         { this.phuCapAnCa = p; }
    public double getPhuCapXang()               { return phuCapXang; }
    public void setPhuCapXang(double p)         { this.phuCapXang = p; }
    public double getPhuCapDienThoai()          { return phuCapDienThoai; }
    public void setPhuCapDienThoai(double p)    { this.phuCapDienThoai = p; }
    public double getThuong()                   { return thuong; }
    public void setThuong(double t)             { this.thuong = t; }
    public double getBhxh()                     { return bhxh; }
    public void setBhxh(double b)               { this.bhxh = b; }
    public double getBhyt()                     { return bhyt; }
    public void setBhyt(double b)               { this.bhyt = b; }
    public double getBhtn()                     { return bhtn; }
    public void setBhtn(double b)               { this.bhtn = b; }
    public double getThueTNCN()                 { return thueTNCN; }
    public void setThueTNCN(double t)           { this.thueTNCN = t; }
    public double getKhauTruKhac()              { return khauTruKhac; }
    public void setKhauTruKhac(double k)        { this.khauTruKhac = k; }
    public double getLuongThucLanh()            { return luongThucLanh; }
    public void setLuongThucLanh(double l)      { this.luongThucLanh = l; }
    public TrangThai getTrangThai()             { return trangThai; }
    public void setTrangThai(TrangThai t)       { this.trangThai = t; }
    public String getGhiChu()                   { return ghiChu; }
    public void setGhiChu(String g)             { this.ghiChu = g; }
    public String getNgayThanhToan()            { return ngayThanhToan; }
    public void setNgayThanhToan(String d)      { this.ngayThanhToan = d; }

    // ---- Computed helpers ----
    public double getTongPhuCap()    { return phuCapAnCa + phuCapXang + phuCapDienThoai; }
    public double getTongKhauTru()   { return bhxh + bhyt + bhtn + thueTNCN + khauTruKhac; }
    public double getLuongTheoNgay() {
        if (ngayCongChuanThang == 0) return luongNgach;
        return luongNgach / ngayCongChuanThang * (ngayCongThucTe + phepNam);
    }
    public double getThuNhapTruocKhauTru() {
        return getLuongTheoNgay() + getTongPhuCap() + thuong;
    }

    public String getTrangThaiLabel() {
        return switch (trangThai) {
            case CHO_DUYET     -> "Chờ duyệt";
            case DA_DUYET      -> "Đã duyệt";
            case DA_THANH_TOAN -> "Đã thanh toán";
            case HUY           -> "Huỷ";
        };
    }

    public String getTrangThaiClass() {
        return switch (trangThai) {
            case CHO_DUYET     -> "badge-warning";
            case DA_DUYET      -> "badge-info";
            case DA_THANH_TOAN -> "badge-success";
            case HUY           -> "badge-danger";
        };
    }
}

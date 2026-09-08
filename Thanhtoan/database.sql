-- Create Database
CREATE DATABASE IF NOT EXISTS miximoi_hrm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE miximoi_hrm;

-- Table: NhanVien
CREATE TABLE IF NOT EXISTS NhanVien (
    id INT AUTO_INCREMENT PRIMARY KEY,
    maNV VARCHAR(20) NOT NULL UNIQUE,
    hoTen VARCHAR(100) NOT NULL,
    chucVu VARCHAR(50),
    phongBan VARCHAR(50),
    luongCoBan DOUBLE NOT NULL DEFAULT 0,
    heSoLuong DOUBLE NOT NULL DEFAULT 1.0,
    email VARCHAR(100),
    sdt VARCHAR(20)
);

-- Table: BangLuong
CREATE TABLE IF NOT EXISTS BangLuong (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nhanVienId INT NOT NULL,
    thang INT NOT NULL,
    nam INT NOT NULL,
    ngayCongChuanThang INT NOT NULL DEFAULT 22,
    ngayCongThucTe INT NOT NULL DEFAULT 0,
    phepNam INT NOT NULL DEFAULT 0,
    nghiPhep INT NOT NULL DEFAULT 0,
    
    luongNgach DOUBLE NOT NULL DEFAULT 0,
    phuCapAnCa DOUBLE NOT NULL DEFAULT 0,
    phuCapXang DOUBLE NOT NULL DEFAULT 0,
    phuCapDienThoai DOUBLE NOT NULL DEFAULT 0,
    thuong DOUBLE NOT NULL DEFAULT 0,
    
    bhxh DOUBLE NOT NULL DEFAULT 0,
    bhyt DOUBLE NOT NULL DEFAULT 0,
    bhtn DOUBLE NOT NULL DEFAULT 0,
    thueTNCN DOUBLE NOT NULL DEFAULT 0,
    khauTruKhac DOUBLE NOT NULL DEFAULT 0,
    
    luongThucLanh DOUBLE NOT NULL DEFAULT 0,
    trangThai VARCHAR(20) NOT NULL DEFAULT 'CHO_DUYET',
    ghiChu VARCHAR(255),
    ngayThanhToan VARCHAR(20),
    
    FOREIGN KEY (nhanVienId) REFERENCES NhanVien(id) ON DELETE CASCADE,
    UNIQUE KEY (nhanVienId, thang, nam)
);

-- Insert Sample Data for NhanVien
INSERT INTO NhanVien (maNV, hoTen, chucVu, phongBan, luongCoBan, heSoLuong, email, sdt) VALUES
('MM001', 'Nguyễn Văn An', 'Giám đốc', 'Ban Giám Đốc', 5000000, 4.98, 'an.nguyen@miximoi.vn', '0901234567'),
('MM002', 'Trần Thị Bình', 'Trưởng phòng', 'Phòng Nhân Sự', 3600000, 3.50, 'binh.tran@miximoi.vn', '0912345678'),
('MM003', 'Lê Minh Cường', 'Kế toán trưởng', 'Phòng Kế Toán', 3600000, 3.20, 'cuong.le@miximoi.vn', '0923456789'),
('MM004', 'Phạm Thị Dung', 'Nhân viên', 'Phòng Nhân Sự', 3000000, 2.10, 'dung.pham@miximoi.vn', '0934567890'),
('MM005', 'Đỗ Văn Hùng', 'Lập trình viên', 'Phòng IT', 3000000, 2.80, 'hung.do@miximoi.vn', '0945678901'),
('MM006', 'Hoàng Thị Lan', 'Senior Dev', 'Phòng IT', 3000000, 3.50, 'lan.hoang@miximoi.vn', '0956789012'),
('MM007', 'Vũ Đức Mạnh', 'Kỹ sư QA', 'Phòng IT', 3000000, 2.50, 'manh.vu@miximoi.vn', '0967890123'),
('MM008', 'Ngô Thị Ngọc', 'Nhân viên KT', 'Phòng Kế Toán', 3000000, 2.00, 'ngoc.ngo@miximoi.vn', '0978901234');

-- Sample BangLuong will be handled dynamically in code if table is empty.

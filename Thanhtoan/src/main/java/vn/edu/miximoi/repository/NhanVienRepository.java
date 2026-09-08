package vn.edu.miximoi.repository;

import vn.edu.miximoi.model.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienRepository {

    // Mock data fallback
    private static final List<NhanVien> MOCK_DATA = new ArrayList<>();
    static {
        MOCK_DATA.add(new NhanVien(1, "MM001", "Nguyễn Văn An",   "Giám đốc",        "Ban Giám Đốc",   5_000_000, 4.98, "an.nguyen@miximoi.vn",     "0901 234 567"));
        MOCK_DATA.add(new NhanVien(2, "MM002", "Trần Thị Bình",   "Trưởng phòng",    "Phòng Nhân Sự",  3_600_000, 3.50, "binh.tran@miximoi.vn",     "0912 345 678"));
        MOCK_DATA.add(new NhanVien(3, "MM003", "Lê Minh Cường",   "Kế toán trưởng",  "Phòng Kế Toán",  3_600_000, 3.20, "cuong.le@miximoi.vn",      "0923 456 789"));
        MOCK_DATA.add(new NhanVien(4, "MM004", "Phạm Thị Dung",   "Nhân viên",       "Phòng Nhân Sự",  3_000_000, 2.10, "dung.pham@miximoi.vn",     "0934 567 890"));
        MOCK_DATA.add(new NhanVien(5, "MM005", "Đỗ Văn Hùng",     "Lập trình viên",  "Phòng IT",       3_000_000, 2.80, "hung.do@miximoi.vn",       "0945 678 901"));
        MOCK_DATA.add(new NhanVien(6, "MM006", "Hoàng Thị Lan",   "Senior Dev",      "Phòng IT",       3_000_000, 3.50, "lan.hoang@miximoi.vn",     "0956 789 012"));
        MOCK_DATA.add(new NhanVien(7, "MM007", "Vũ Đức Mạnh",     "Kỹ sư QA",       "Phòng IT",       3_000_000, 2.50, "manh.vu@miximoi.vn",       "0967 890 123"));
        MOCK_DATA.add(new NhanVien(8, "MM008", "Ngô Thị Ngọc",    "Nhân viên KT",    "Phòng Kế Toán",  3_000_000, 2.00, "ngoc.ngo@miximoi.vn",      "0978 901 234"));
    }

    public List<NhanVien> findAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) return MOCK_DATA; // Fallback
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToNhanVien(rs));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list.isEmpty() ? MOCK_DATA : list;
    }

    public NhanVien findById(int id) {
        String sql = "SELECT * FROM NhanVien WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) return MOCK_DATA.stream().filter(nv -> nv.getId() == id).findFirst().orElse(null);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return mapResultSetToNhanVien(rs);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return MOCK_DATA.stream().filter(nv -> nv.getId() == id).findFirst().orElse(null);
    }

    private NhanVien mapResultSetToNhanVien(ResultSet rs) throws SQLException {
        NhanVien nv = new NhanVien();
        nv.setId(rs.getInt("id"));
        nv.setMaNV(rs.getString("maNV"));
        nv.setHoTen(rs.getString("hoTen"));
        nv.setChucVu(rs.getString("chucVu"));
        nv.setPhongBan(rs.getString("phongBan"));
        nv.setLuongCoBan(rs.getDouble("luongCoBan"));
        nv.setHeSoLuong(rs.getDouble("heSoLuong"));
        nv.setEmail(rs.getString("email"));
        nv.setSdt(rs.getString("sdt"));
        return nv;
    }
}

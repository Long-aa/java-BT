package vn.edu.miximoi.repository;

import vn.edu.miximoi.model.BangLuong;
import vn.edu.miximoi.model.BangLuong.TrangThai;
import vn.edu.miximoi.model.NhanVien;
import vn.edu.miximoi.model.PhieuLuong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BangLuongRepository {

    // Mock data fallback
    private static final List<BangLuong> MOCK_DATA = new ArrayList<>();
    private static int autoId = 9;
    private static final NhanVienRepository nvRepo = new NhanVienRepository();

    static {
        int[] ngayCong = {22, 20, 22, 21, 18, 22, 19, 22};
        int[] phep     = { 0,  1,  0,  0,  2,  0,  1,  0};
        double[] thuong = {2_000_000, 0, 500_000, 0, 0, 1_000_000, 0, 0};
        TrangThai[] tt = {
            TrangThai.DA_THANH_TOAN, TrangThai.DA_THANH_TOAN,
            TrangThai.DA_DUYET,      TrangThai.DA_DUYET,
            TrangThai.CHO_DUYET,     TrangThai.CHO_DUYET,
            TrangThai.CHO_DUYET,     TrangThai.DA_THANH_TOAN
        };

        List<NhanVien> nhanViens = nvRepo.findAll();
        for (int i = 0; i < nhanViens.size(); i++) {
            NhanVien nv = nhanViens.get(i);
            BangLuong bl = buildMock(i + 1, nv, 8, 2026, 22, ngayCong[i], phep[i], thuong[i], tt[i]);
            if (tt[i] == TrangThai.DA_THANH_TOAN) bl.setNgayThanhToan("2026-09-01");
            MOCK_DATA.add(bl);
        }
    }

    private static BangLuong buildMock(int id, NhanVien nv, int thang, int nam,
                                    int chuanThang, int thucTe, int phepNam,
                                    double thuong, TrangThai trangThai) {
        BangLuong bl = new BangLuong();
        bl.setId(id); bl.setNhanVienId(nv.getId()); bl.setThang(thang); bl.setNam(nam);
        bl.setNgayCongChuanThang(chuanThang); bl.setNgayCongThucTe(thucTe); bl.setPhepNam(phepNam);
        bl.setLuongNgach(nv.getLuongNgach());
        boolean isManager = nv.getHeSoLuong() >= 3.0;
        bl.setPhuCapAnCa(730_000); bl.setPhuCapXang(isManager ? 500_000 : 300_000); bl.setPhuCapDienThoai(isManager ? 300_000 : 150_000);
        bl.setThuong(thuong);
        double luongDong = nv.getLuongNgach();
        bl.setBhxh(luongDong * 0.08); bl.setBhyt(luongDong * 0.015); bl.setBhtn(luongDong * 0.01);
        double thuNhap = bl.getLuongTheoNgay() + bl.getTongPhuCap() + thuong;
        bl.setThueTNCN(Math.max(0, (thuNhap - 11_000_000) * 0.05));
        bl.setLuongThucLanh(thuNhap - bl.getTongKhauTru());
        bl.setTrangThai(trangThai);
        return bl;
    }

    public List<BangLuong> findByThangNam(int thang, int nam) {
        List<BangLuong> list = new ArrayList<>();
        String sql = "SELECT * FROM BangLuong WHERE thang = ? AND nam = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) return MOCK_DATA.stream().filter(bl -> bl.getThang() == thang && bl.getNam() == nam).collect(Collectors.toList());
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, thang); ps.setInt(2, nam);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) list.add(mapRs(rs));
                }
            }
            return list;
        } catch (SQLException e) { e.printStackTrace(); }
        return MOCK_DATA.stream().filter(bl -> bl.getThang() == thang && bl.getNam() == nam).collect(Collectors.toList());
    }

    public BangLuong findById(int id) {
        String sql = "SELECT * FROM BangLuong WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) return MOCK_DATA.stream().filter(bl -> bl.getId() == id).findFirst().orElse(null);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return mapRs(rs);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return MOCK_DATA.stream().filter(bl -> bl.getId() == id).findFirst().orElse(null);
    }

    public List<PhieuLuong> getPhieuLuong(int thang, int nam) {
        return findByThangNam(thang, nam).stream()
            .map(bl -> new PhieuLuong(nvRepo.findById(bl.getNhanVienId()), bl))
            .collect(Collectors.toList());
    }

    public PhieuLuong getPhieuLuongById(int id) {
        BangLuong bl = findById(id);
        if (bl == null) return null;
        return new PhieuLuong(nvRepo.findById(bl.getNhanVienId()), bl);
    }

    public void save(BangLuong bl) {
        String sqlInsert = "INSERT INTO BangLuong (nhanVienId, thang, nam, ngayCongChuanThang, ngayCongThucTe, phepNam, nghiPhep, luongNgach, phuCapAnCa, phuCapXang, phuCapDienThoai, thuong, bhxh, bhyt, bhtn, thueTNCN, khauTruKhac, luongThucLanh, trangThai, ghiChu) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String sqlUpdate = "UPDATE BangLuong SET ngayCongChuanThang=?, ngayCongThucTe=?, phepNam=?, luongNgach=?, phuCapAnCa=?, phuCapXang=?, phuCapDienThoai=?, thuong=?, bhxh=?, bhyt=?, bhtn=?, thueTNCN=?, khauTruKhac=?, luongThucLanh=?, ghiChu=? WHERE id=?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) {
                if (bl.getId() == 0) { bl.setId(autoId++); MOCK_DATA.add(bl); } 
                else {
                    BangLuong existing = MOCK_DATA.stream().filter(x -> x.getId() == bl.getId()).findFirst().orElse(null);
                    if (existing != null) MOCK_DATA.set(MOCK_DATA.indexOf(existing), bl);
                }
                return;
            }
            if (bl.getId() == 0) {
                try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, bl.getNhanVienId()); ps.setInt(2, bl.getThang()); ps.setInt(3, bl.getNam());
                    ps.setInt(4, bl.getNgayCongChuanThang()); ps.setInt(5, bl.getNgayCongThucTe()); ps.setInt(6, bl.getPhepNam()); ps.setInt(7, bl.getNghiPhep());
                    ps.setDouble(8, bl.getLuongNgach()); ps.setDouble(9, bl.getPhuCapAnCa()); ps.setDouble(10, bl.getPhuCapXang()); ps.setDouble(11, bl.getPhuCapDienThoai()); ps.setDouble(12, bl.getThuong());
                    ps.setDouble(13, bl.getBhxh()); ps.setDouble(14, bl.getBhyt()); ps.setDouble(15, bl.getBhtn()); ps.setDouble(16, bl.getThueTNCN()); ps.setDouble(17, bl.getKhauTruKhac()); ps.setDouble(18, bl.getLuongThucLanh());
                    ps.setString(19, bl.getTrangThai().name()); ps.setString(20, bl.getGhiChu());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                    ps.setInt(1, bl.getNgayCongChuanThang()); ps.setInt(2, bl.getNgayCongThucTe()); ps.setInt(3, bl.getPhepNam());
                    ps.setDouble(4, bl.getLuongNgach()); ps.setDouble(5, bl.getPhuCapAnCa()); ps.setDouble(6, bl.getPhuCapXang()); ps.setDouble(7, bl.getPhuCapDienThoai()); ps.setDouble(8, bl.getThuong());
                    ps.setDouble(9, bl.getBhxh()); ps.setDouble(10, bl.getBhyt()); ps.setDouble(11, bl.getBhtn()); ps.setDouble(12, bl.getThueTNCN()); ps.setDouble(13, bl.getKhauTruKhac()); ps.setDouble(14, bl.getLuongThucLanh());
                    ps.setString(15, bl.getGhiChu()); ps.setInt(16, bl.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void xacNhanThanhToan(int id) {
        String sql = "UPDATE BangLuong SET trangThai = 'DA_THANH_TOAN', ngayThanhToan = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) {
                BangLuong bl = findById(id);
                if (bl != null) { bl.setTrangThai(TrangThai.DA_THANH_TOAN); bl.setNgayThanhToan(LocalDate.now().toString()); }
                return;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, LocalDate.now().toString()); ps.setInt(2, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void xacNhanThanhToanHangLoat(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;
        String sql = "UPDATE BangLuong SET trangThai = 'DA_THANH_TOAN', ngayThanhToan = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) {
                for (int id : ids) xacNhanThanhToan(id);
                return;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                String today = LocalDate.now().toString();
                for (int id : ids) {
                    ps.setString(1, today); ps.setInt(2, id);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void duyet(int id) {
        String sql = "UPDATE BangLuong SET trangThai = 'DA_DUYET' WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) { BangLuong bl = findById(id); if (bl != null) bl.setTrangThai(TrangThai.DA_DUYET); return; }
            try (PreparedStatement ps = conn.prepareStatement(sql)) { ps.setInt(1, id); ps.executeUpdate(); }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void huy(int id) {
        String sql = "UPDATE BangLuong SET trangThai = 'HUY' WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) { BangLuong bl = findById(id); if (bl != null) bl.setTrangThai(TrangThai.HUY); return; }
            try (PreparedStatement ps = conn.prepareStatement(sql)) { ps.setInt(1, id); ps.executeUpdate(); }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM BangLuong WHERE id = ? AND trangThai = 'CHO_DUYET'";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) {
                BangLuong bl = findById(id);
                if (bl != null && bl.getTrangThai() == TrangThai.CHO_DUYET) {
                    MOCK_DATA.remove(bl);
                }
                return;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public boolean checkExists(int nhanVienId, int thang, int nam, int currentId) {
        String sql = "SELECT COUNT(*) FROM BangLuong WHERE nhanVienId = ? AND thang = ? AND nam = ? AND id != ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) {
                return MOCK_DATA.stream().anyMatch(bl -> bl.getNhanVienId() == nhanVienId 
                                                      && bl.getThang() == thang 
                                                      && bl.getNam() == nam 
                                                      && bl.getId() != currentId);
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, nhanVienId);
                ps.setInt(2, thang);
                ps.setInt(3, nam);
                ps.setInt(4, currentId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public double tongLuongThanhToan(int thang, int nam) {
        return getPhieuLuong(thang, nam).stream().filter(pl -> pl.getBangLuong().getTrangThai() == TrangThai.DA_THANH_TOAN).mapToDouble(pl -> pl.getBangLuong().getLuongThucLanh()).sum();
    }

    public double tongLuongCho(int thang, int nam) {
        return getPhieuLuong(thang, nam).stream().filter(pl -> pl.getBangLuong().getTrangThai() == TrangThai.DA_DUYET || pl.getBangLuong().getTrangThai() == TrangThai.CHO_DUYET).mapToDouble(pl -> pl.getBangLuong().getLuongThucLanh()).sum();
    }

    private BangLuong mapRs(ResultSet rs) throws SQLException {
        BangLuong bl = new BangLuong();
        bl.setId(rs.getInt("id")); bl.setNhanVienId(rs.getInt("nhanVienId"));
        bl.setThang(rs.getInt("thang")); bl.setNam(rs.getInt("nam"));
        bl.setNgayCongChuanThang(rs.getInt("ngayCongChuanThang")); bl.setNgayCongThucTe(rs.getInt("ngayCongThucTe"));
        bl.setPhepNam(rs.getInt("phepNam")); bl.setNghiPhep(rs.getInt("nghiPhep"));
        bl.setLuongNgach(rs.getDouble("luongNgach")); bl.setPhuCapAnCa(rs.getDouble("phuCapAnCa"));
        bl.setPhuCapXang(rs.getDouble("phuCapXang")); bl.setPhuCapDienThoai(rs.getDouble("phuCapDienThoai"));
        bl.setThuong(rs.getDouble("thuong")); bl.setBhxh(rs.getDouble("bhxh")); bl.setBhyt(rs.getDouble("bhyt"));
        bl.setBhtn(rs.getDouble("bhtn")); bl.setThueTNCN(rs.getDouble("thueTNCN")); bl.setKhauTruKhac(rs.getDouble("khauTruKhac"));
        bl.setLuongThucLanh(rs.getDouble("luongThucLanh"));
        bl.setTrangThai(TrangThai.valueOf(rs.getString("trangThai")));
        bl.setGhiChu(rs.getString("ghiChu")); bl.setNgayThanhToan(rs.getString("ngayThanhToan"));
        return bl;
    }
}

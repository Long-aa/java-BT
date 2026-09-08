package vn.edu.miximoi.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.miximoi.model.BangLuong;
import vn.edu.miximoi.model.BangLuong.TrangThai;
import vn.edu.miximoi.model.NhanVien;
import vn.edu.miximoi.repository.BangLuongRepository;
import vn.edu.miximoi.repository.NhanVienRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import vn.edu.miximoi.model.PhieuLuong;

@WebServlet("/bang-luong")
public class BangLuongController extends HttpServlet {
    private final BangLuongRepository blRepo = new BangLuongRepository();
    private final NhanVienRepository nvRepo   = new NhanVienRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("detail".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("pl", blRepo.getPhieuLuongById(id));
            forward(req, resp, "/views/luong/phieu.jsp"); return;
        }

        if ("new".equals(action)) {
            req.setAttribute("dsNhanVien", nvRepo.findAll());
            req.setAttribute("thangNay", LocalDate.now().getMonthValue());
            req.setAttribute("namNay",   LocalDate.now().getYear());
            forward(req, resp, "/views/luong/form.jsp"); return;
        }

        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("pl", blRepo.getPhieuLuongById(id));
            req.setAttribute("dsNhanVien", nvRepo.findAll());
            forward(req, resp, "/views/luong/form.jsp"); return;
        }

        if ("duyet".equals(action)) {
            blRepo.duyet(Integer.parseInt(req.getParameter("id")));
            redirect(req, resp, "/bang-luong?" + buildQs(req)); return;
        }

        if ("huy".equals(action)) {
            blRepo.huy(Integer.parseInt(req.getParameter("id")));
            redirect(req, resp, "/bang-luong?" + buildQs(req)); return;
        }

        if ("delete".equals(action)) {
            blRepo.delete(Integer.parseInt(req.getParameter("id")));
            redirect(req, resp, "/bang-luong?" + buildQs(req)); return;
        }

        // Default: hiển thị danh sách theo tháng
        int thang = paramInt(req, "thang", LocalDate.now().getMonthValue());
        int nam   = paramInt(req, "nam",   LocalDate.now().getYear());

        req.setAttribute("dsPhieuLuong",      blRepo.getPhieuLuong(thang, nam));
        req.setAttribute("tongThanhToan",      blRepo.tongLuongThanhToan(thang, nam));
        req.setAttribute("tongCho",            blRepo.tongLuongCho(thang, nam));
        req.setAttribute("thang", thang);
        req.setAttribute("nam",   nam);
        forward(req, resp, "/views/luong/list.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int    id        = paramInt(req, "id", 0);
        int    nvId      = paramInt(req, "nhanVienId", 0);
        int    thang     = paramInt(req, "thang", LocalDate.now().getMonthValue());
        int    nam       = paramInt(req, "nam",   LocalDate.now().getYear());
        int    chuanThang= paramInt(req, "ngayCongChuanThang", 22);
        int    thucTe    = paramInt(req, "ngayCongThucTe", 22);
        int    phepNam   = paramInt(req, "phepNam", 0);

        NhanVien nv = nvRepo.findById(nvId);
        if (nv == null) { redirect(req, resp, "/bang-luong"); return; }

        BangLuong bl = id > 0 ? blRepo.findById(id) : new BangLuong();
        if (bl == null) bl = new BangLuong();

        bl.setId(id);
        bl.setNhanVienId(nvId);
        bl.setThang(thang); bl.setNam(nam);
        bl.setNgayCongChuanThang(chuanThang);
        bl.setNgayCongThucTe(thucTe);
        bl.setPhepNam(phepNam);
        bl.setLuongNgach(nv.getLuongNgach());
        bl.setPhuCapAnCa(paramDouble(req, "phuCapAnCa", 730_000));
        bl.setPhuCapXang(paramDouble(req, "phuCapXang", 300_000));
        bl.setPhuCapDienThoai(paramDouble(req, "phuCapDienThoai", 150_000));
        bl.setThuong(paramDouble(req, "thuong", 0));

        double luongDong = nv.getLuongNgach();
        bl.setBhxh(luongDong * 0.08);
        bl.setBhyt(luongDong * 0.015);
        bl.setBhtn(luongDong * 0.01);
        double thuNhap = bl.getLuongTheoNgay() + bl.getTongPhuCap() + bl.getThuong();
        bl.setThueTNCN(Math.max(0, (thuNhap - 11_000_000) * 0.05));
        bl.setKhauTruKhac(paramDouble(req, "khauTruKhac", 0));
        bl.setLuongThucLanh(thuNhap - bl.getTongKhauTru());
        bl.setGhiChu(req.getParameter("ghiChu"));

        if (blRepo.checkExists(nvId, thang, nam, id)) {
            req.setAttribute("error", "Nhân viên này đã có bảng lương trong tháng " + thang + "/" + nam + "!");
            req.setAttribute("pl", new PhieuLuong(nv, bl));
            req.setAttribute("dsNhanVien", nvRepo.findAll());
            forward(req, resp, "/views/luong/form.jsp");
            return;
        }

        blRepo.save(bl);
        redirect(req, resp, "/bang-luong?thang=" + thang + "&nam=" + nam);
    }

    // ---- helpers ----
    private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }

    private void redirect(HttpServletRequest req, HttpServletResponse resp, String path)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + path);
    }

    private int paramInt(HttpServletRequest req, String name, int def) {
        String v = req.getParameter(name);
        if (v == null || v.isBlank()) return def;
        try { return Integer.parseInt(v.trim()); } catch (NumberFormatException e) { return def; }
    }

    private double paramDouble(HttpServletRequest req, String name, double def) {
        String v = req.getParameter(name);
        if (v == null || v.isBlank()) return def;
        try { return Double.parseDouble(v.trim().replace(",", "")); } catch (NumberFormatException e) { return def; }
    }

    private String buildQs(HttpServletRequest req) {
        String t = req.getParameter("thang");
        String n = req.getParameter("nam");
        return (t != null ? "thang=" + t : "") + (n != null ? "&nam=" + n : "");
    }
}

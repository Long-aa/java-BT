package vn.edu.miximoi.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.miximoi.model.BangLuong.TrangThai;
import vn.edu.miximoi.model.PhieuLuong;
import vn.edu.miximoi.repository.BangLuongRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/thanh-toan")
public class ThanhToanController extends HttpServlet {
    private final BangLuongRepository blRepo = new BangLuongRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        
        int thang = paramInt(req, "thang", LocalDate.now().getMonthValue());
        int nam   = paramInt(req, "nam",   LocalDate.now().getYear());

        if ("export".equals(action)) {
            exportCSV(req, resp, thang, nam);
            return;
        }

        if ("confirm".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            PhieuLuong pl = blRepo.getPhieuLuongById(id);
            if (pl != null && pl.getBangLuong().getTrangThai() == TrangThai.DA_DUYET) {
                req.setAttribute("pl", pl);
                forward(req, resp, "/views/thanh-toan/confirm.jsp");
            } else {
                redirect(req, resp, "/thanh-toan?thang=" + thang + "&nam=" + nam);
            }
            return;
        }

        List<PhieuLuong> danhSach = blRepo.getPhieuLuong(thang, nam).stream()
                .filter(pl -> pl.getBangLuong().getTrangThai() == TrangThai.DA_DUYET 
                           || pl.getBangLuong().getTrangThai() == TrangThai.DA_THANH_TOAN)
                .collect(Collectors.toList());

        req.setAttribute("dsPhieuLuong", danhSach);
        req.setAttribute("thang", thang);
        req.setAttribute("nam", nam);
        forward(req, resp, "/views/thanh-toan/list.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        
        if ("xacnhan".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            blRepo.xacNhanThanhToan(id);
        } else if ("batch_pay".equals(action)) {
            String[] idsParam = req.getParameterValues("ids");
            if (idsParam != null && idsParam.length > 0) {
                List<Integer> ids = new ArrayList<>();
                for (String s : idsParam) ids.add(Integer.parseInt(s));
                blRepo.xacNhanThanhToanHangLoat(ids);
            }
        }
        
        String t = req.getParameter("thang");
        String n = req.getParameter("nam");
        String qs = (t != null ? "thang=" + t : "") + (n != null ? "&nam=" + n : "");
        redirect(req, resp, "/thanh-toan" + (qs.isEmpty() ? "" : "?" + qs));
    }
    
    private void exportCSV(HttpServletRequest req, HttpServletResponse resp, int thang, int nam) throws IOException {
        resp.setContentType("text/csv; charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=\"ThanhToan_T" + thang + "_" + nam + ".csv\"");
        resp.setCharacterEncoding("UTF-8");
        
        // BOM for Excel UTF-8
        PrintWriter out = resp.getWriter();
        out.write('\ufeff');
        out.println("Mã NV,Họ Tên,Ngân Hàng,Số Tài Khoản,Số Tiền,Trạng Thái");
        
        List<PhieuLuong> danhSach = blRepo.getPhieuLuong(thang, nam).stream()
            .filter(pl -> pl.getBangLuong().getTrangThai() == TrangThai.DA_DUYET || pl.getBangLuong().getTrangThai() == TrangThai.DA_THANH_TOAN)
            .collect(Collectors.toList());
            
        for (PhieuLuong pl : danhSach) {
            String row = String.format("%s,\"%s\",Vietcombank,0123456789,%.0f,%s",
                pl.getNhanVien().getMaNV(),
                pl.getNhanVien().getHoTen(),
                pl.getBangLuong().getLuongThucLanh(),
                pl.getBangLuong().getTrangThaiLabel()
            );
            out.println(row);
        }
        out.flush();
        out.close();
    }

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
}

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Phiếu Lương - MixiMoi HRM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        .phieu-luong { max-width: 800px; margin: 0 auto; background: var(--bg-card); padding: 2rem; border-radius: var(--radius); border: 1px solid var(--border-color); }
        .pl-header { text-align: center; margin-bottom: 2rem; padding-bottom: 1rem; border-bottom: 2px dashed var(--border-color); }
        .pl-header h2 { font-size: 1.5rem; margin-bottom: 0.5rem; color: var(--primary); }
        .pl-info { margin-bottom: 2rem; display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
        .pl-row { display: flex; justify-content: space-between; padding: 0.75rem 0; border-bottom: 1px solid var(--border-color); }
        .pl-row:last-child { border-bottom: none; }
        .pl-section-title { font-weight: 600; color: var(--info); margin-top: 1.5rem; margin-bottom: 0.5rem; text-transform: uppercase; font-size: 0.85rem; }
        .pl-total { margin-top: 2rem; padding-top: 1rem; border-top: 2px solid var(--border-color); display: flex; justify-content: space-between; font-size: 1.25rem; font-weight: 700; color: var(--success); }
    </style>
</head>
<body>
    <div class="container">
        <header style="border-bottom: none;">
            <a href="${pageContext.request.contextPath}/bang-luong" class="btn btn-outline">&larr; Trở Về</a>
            <button onclick="window.print()" class="btn btn-primary">In Phiếu</button>
        </header>

        <div class="phieu-luong">
            <div class="pl-header">
                <h2>PHIẾU LƯƠNG NHÂN VIÊN</h2>
                <p>Tháng ${pl.bangLuong.thang} Năm ${pl.bangLuong.nam}</p>
            </div>

            <div class="pl-info">
                <div>
                    <p><strong>Họ và tên:</strong> ${pl.nhanVien.hoTen}</p>
                    <p><strong>Mã NV:</strong> ${pl.nhanVien.maNV}</p>
                    <p><strong>Chức vụ:</strong> ${pl.nhanVien.chucVu}</p>
                </div>
                <div>
                    <p><strong>Phòng ban:</strong> ${pl.nhanVien.phongBan}</p>
                    <p><strong>Trạng thái:</strong> <span class="badge ${pl.bangLuong.trangThaiClass}">${pl.bangLuong.trangThaiLabel}</span></p>
                    <p><strong>Ngày thanh toán:</strong> ${pl.bangLuong.ngayThanhToan != null ? pl.bangLuong.ngayThanhToan : 'Chưa'}</p>
                </div>
            </div>

            <div class="pl-section-title">1. Thu Nhập</div>
            <div class="pl-row">
                <span>Lương cơ bản (Hệ số ${pl.nhanVien.heSoLuong})</span>
                <span class="currency"><fmt:formatNumber value="${pl.bangLuong.luongNgach}" pattern="#,###"/></span>
            </div>
            <div class="pl-row">
                <span>Lương theo ngày công (${pl.bangLuong.ngayCongThucTe} / ${pl.bangLuong.ngayCongChuanThang} ngày)</span>
                <span class="currency"><fmt:formatNumber value="${pl.bangLuong.luongTheoNgay}" pattern="#,###"/></span>
            </div>
            <div class="pl-row">
                <span>Phụ cấp ăn ca</span>
                <span class="currency"><fmt:formatNumber value="${pl.bangLuong.phuCapAnCa}" pattern="#,###"/></span>
            </div>
            <div class="pl-row">
                <span>Phụ cấp xăng xe / điện thoại</span>
                <span class="currency"><fmt:formatNumber value="${pl.bangLuong.phuCapXang + pl.bangLuong.phuCapDienThoai}" pattern="#,###"/></span>
            </div>
            <div class="pl-row">
                <span>Thưởng khác</span>
                <span class="currency"><fmt:formatNumber value="${pl.bangLuong.thuong}" pattern="#,###"/></span>
            </div>

            <div class="pl-section-title">2. Khấu Trừ</div>
            <div class="pl-row">
                <span>BHXH (8%), BHYT (1.5%), BHTN (1%)</span>
                <span class="currency"><fmt:formatNumber value="${pl.bangLuong.bhxh + pl.bangLuong.bhyt + pl.bangLuong.bhtn}" pattern="#,###"/></span>
            </div>
            <div class="pl-row">
                <span>Thuế TNCN</span>
                <span class="currency"><fmt:formatNumber value="${pl.bangLuong.thueTNCN}" pattern="#,###"/></span>
            </div>

            <div class="pl-total">
                <span>THỰC LÃNH:</span>
                <span class="currency"><fmt:formatNumber value="${pl.bangLuong.luongThucLanh}" pattern="#,###"/></span>
            </div>
            
            <c:if test="${not empty pl.bangLuong.ghiChu}">
                <div style="margin-top: 1.5rem; font-style: italic; color: var(--text-muted); text-align: center;">
                    * Ghi chú: ${pl.bangLuong.ghiChu}
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>

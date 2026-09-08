<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lập Bảng Lương - MixiMoi HRM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <header>
            <div class="logo">
                <h1>MixiMoi <span>HRM System</span></h1>
            </div>
            <nav>
                <a href="${pageContext.request.contextPath}/bang-luong" class="active">Bảng Lương</a>
                <a href="${pageContext.request.contextPath}/thanh-toan">Thanh Toán</a>
            </nav>
        </header>

        <div class="card" style="max-width: 800px; margin: 0 auto;">
            <div class="card-header">
                <h2 class="card-title">${not empty pl ? 'Cập Nhật Lương' : 'Lập Bảng Lương Mới'}</h2>
                <a href="${pageContext.request.contextPath}/bang-luong" class="btn btn-outline">Trở Về</a>
            </div>

            <c:if test="${not empty error}">
                <div style="background-color: var(--danger); color: white; padding: 1rem; border-radius: var(--radius); margin-bottom: 1.5rem;">
                    <strong>Lỗi:</strong> ${error}
                </div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/bang-luong">
                <input type="hidden" name="id" value="${not empty pl ? pl.bangLuong.id : 0}">
                
                <div class="grid-2">
                    <div class="form-group">
                        <label class="form-label">Kỳ Lương (Tháng/Năm)</label>
                        <div style="display:flex; gap: 10px;">
                            <input type="number" name="thang" class="form-control" value="${not empty pl ? pl.bangLuong.thang : thangNay}" required min="1" max="12">
                            <input type="number" name="nam" class="form-control" value="${not empty pl ? pl.bangLuong.nam : namNay}" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Nhân Viên</label>
                        <select name="nhanVienId" class="form-control" required ${not empty pl ? 'disabled' : ''}>
                            <c:forEach var="nv" items="${dsNhanVien}">
                                <option value="${nv.id}" ${not empty pl && pl.nhanVien.id == nv.id ? 'selected' : ''}>
                                    ${nv.maNV} - ${nv.hoTen} (${nv.phongBan})
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty pl}">
                            <input type="hidden" name="nhanVienId" value="${pl.nhanVien.id}">
                        </c:if>
                    </div>
                </div>

                <hr style="border-color: var(--border-color); margin: 1.5rem 0;">

                <div class="grid-3">
                    <div class="form-group">
                        <label class="form-label">Ngày Công Chuẩn</label>
                        <input type="number" name="ngayCongChuanThang" class="form-control" value="${not empty pl ? pl.bangLuong.ngayCongChuanThang : 22}" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Thực Tế Làm Việc</label>
                        <input type="number" name="ngayCongThucTe" class="form-control" value="${not empty pl ? pl.bangLuong.ngayCongThucTe : 22}" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Ngày Nghỉ Phép (Có Lương)</label>
                        <input type="number" name="phepNam" class="form-control" value="${not empty pl ? pl.bangLuong.phepNam : 0}" required>
                    </div>
                </div>

                <div class="grid-2">
                    <div class="form-group">
                        <label class="form-label">Phụ Cấp Ăn Ca (VNĐ)</label>
                        <input type="number" name="phuCapAnCa" class="form-control" value="${not empty pl ? pl.bangLuong.phuCapAnCa : 730000}">
                    </div>
                    <div class="form-group">
                        <label class="form-label">Thưởng Thêm (VNĐ)</label>
                        <input type="number" name="thuong" class="form-control" value="${not empty pl ? pl.bangLuong.thuong : 0}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">Ghi Chú</label>
                    <input type="text" name="ghiChu" class="form-control" value="${not empty pl ? pl.bangLuong.ghiChu : ''}" placeholder="Ghi chú thêm về bảng lương này (nếu có)...">
                </div>

                <div style="margin-top: 2rem; text-align: right;">
                    <button type="submit" class="btn btn-primary" style="padding: 0.75rem 2rem; font-size: 1rem;">${not empty pl ? 'Cập Nhật' : 'Tạo Bảng Lương'}</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

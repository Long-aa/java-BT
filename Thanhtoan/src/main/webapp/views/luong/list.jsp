<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bảng Lương - MixiMoi HRM</title>
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

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-title">Tháng Lương</div>
                <div class="stat-value">${thang}/${nam}</div>
            </div>
            <div class="stat-card">
                <div class="stat-title">Đã Thanh Toán</div>
                <div class="stat-value text-success currency">
                    <fmt:formatNumber value="${tongThanhToan}" pattern="#,###"/>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-title">Chờ Thanh Toán / Duyệt</div>
                <div class="stat-value text-warning currency">
                    <fmt:formatNumber value="${tongCho}" pattern="#,###"/>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Danh sách Bảng Lương</h2>
                <div>
                    <form method="get" action="${pageContext.request.contextPath}/bang-luong" style="display:inline-block; margin-right: 1rem;">
                        <select name="thang" class="form-control" style="width: auto; display: inline-block; padding: 0.4rem;" onchange="this.form.submit()">
                            <c:forEach var="i" begin="1" end="12">
                                <option value="${i}" ${i == thang ? 'selected' : ''}>Tháng ${i}</option>
                            </c:forEach>
                        </select>
                        <select name="nam" class="form-control" style="width: auto; display: inline-block; padding: 0.4rem;" onchange="this.form.submit()">
                            <option value="2026" ${2026 == nam ? 'selected' : ''}>2026</option>
                            <option value="2025" ${2025 == nam ? 'selected' : ''}>2025</option>
                        </select>
                    </form>
                    <a href="${pageContext.request.contextPath}/bang-luong?action=new" class="btn btn-primary">+ Lập Phiếu Mới</a>
                </div>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Mã NV</th>
                        <th>Họ Tên</th>
                        <th>Chức vụ</th>
                        <th class="text-right">Thực Lãnh</th>
                        <th class="text-center">Trạng Thái</th>
                        <th class="text-center">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pl" items="${dsPhieuLuong}">
                        <tr>
                            <td>${pl.nhanVien.maNV}</td>
                            <td>
                                <strong>${pl.nhanVien.hoTen}</strong><br>
                                <small style="color:var(--text-muted)">${pl.nhanVien.phongBan}</small>
                            </td>
                            <td>${pl.nhanVien.chucVu}</td>
                            <td class="text-right currency" style="font-weight:600">
                                <fmt:formatNumber value="${pl.bangLuong.luongThucLanh}" pattern="#,###"/>
                            </td>
                            <td class="text-center">
                                <span class="badge ${pl.bangLuong.trangThaiClass}">${pl.bangLuong.trangThaiLabel}</span>
                            </td>
                            <td class="text-center">
                                <a href="${pageContext.request.contextPath}/bang-luong?action=detail&id=${pl.bangLuong.id}" class="btn btn-outline btn-sm">Xem</a>
                                <c:if test="${pl.bangLuong.trangThai.name() == 'CHO_DUYET'}">
                                    <a href="${pageContext.request.contextPath}/bang-luong?action=edit&id=${pl.bangLuong.id}" class="btn btn-outline btn-sm">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/bang-luong?action=duyet&id=${pl.bangLuong.id}" class="btn btn-success btn-sm">Duyệt</a>
                                    <a href="${pageContext.request.contextPath}/bang-luong?action=delete&id=${pl.bangLuong.id}" class="btn btn-outline btn-sm" style="color:var(--danger); border-color:var(--danger);" onclick="return confirm('Bạn có chắc muốn xóa bảng lương này không?');">Xóa</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty dsPhieuLuong}">
                        <tr>
                            <td colspan="6" class="text-center" style="padding: 2rem;">Chưa có dữ liệu bảng lương tháng này.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>

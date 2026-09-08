<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh Toán Lương - MixiMoi HRM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <script>
        function toggleCheckAll(source) {
            checkboxes = document.getElementsByName('ids');
            for(var i=0, n=checkboxes.length;i<n;i++) {
                if(!checkboxes[i].disabled) checkboxes[i].checked = source.checked;
            }
            updatePayBtn();
        }
        function updatePayBtn() {
            let checked = document.querySelectorAll('input[name="ids"]:checked').length;
            let btn = document.getElementById('btnPaySelected');
            btn.disabled = checked === 0;
            btn.innerHTML = `Thanh toán (${checked}) mục đã chọn`;
        }
    </script>
</head>
<body>
    <div class="container">
        <header>
            <div class="logo">
                <h1>MixiMoi <span>HRM System</span></h1>
            </div>
            <nav>
                <a href="${pageContext.request.contextPath}/bang-luong">Bảng Lương</a>
                <a href="${pageContext.request.contextPath}/thanh-toan" class="active">Thanh Toán</a>
            </nav>
        </header>

        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Danh Sách Cần Thanh Toán</h2>
                <div>
                    <form method="get" action="${pageContext.request.contextPath}/thanh-toan" style="display:inline-block; margin-right: 1rem;">
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
                    <a href="${pageContext.request.contextPath}/thanh-toan?action=export&thang=${thang}&nam=${nam}" class="btn btn-outline" style="margin-right:0.5rem;">📥 Xuất CSV (Ngân Hàng)</a>
                </div>
            </div>

            <form method="post" action="${pageContext.request.contextPath}/thanh-toan">
                <input type="hidden" name="action" value="batch_pay">
                <input type="hidden" name="thang" value="${thang}">
                <input type="hidden" name="nam" value="${nam}">
                <table>
                    <thead>
                        <tr>
                            <th style="width:40px;"><input type="checkbox" onclick="toggleCheckAll(this)"></th>
                            <th>Mã NV</th>
                            <th>Họ Tên / STK</th>
                            <th class="text-right">Số Tiền (VNĐ)</th>
                            <th class="text-center">Trạng Thái</th>
                            <th class="text-center">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pl" items="${dsPhieuLuong}">
                            <tr>
                                <td>
                                    <input type="checkbox" name="ids" value="${pl.bangLuong.id}" 
                                           onclick="updatePayBtn()" 
                                           ${pl.bangLuong.trangThai.name() == 'DA_THANH_TOAN' ? 'disabled' : ''}>
                                </td>
                                <td>${pl.nhanVien.maNV}</td>
                                <td>
                                    <strong>${pl.nhanVien.hoTen}</strong><br>
                                    <small style="color:var(--text-muted)">VCB - 0123456789 (${pl.nhanVien.phongBan})</small>
                                </td>
                                <td class="text-right currency" style="font-weight:600; font-size: 1.1rem; color: var(--success)">
                                    <fmt:formatNumber value="${pl.bangLuong.luongThucLanh}" pattern="#,###"/>
                                </td>
                                <td class="text-center">
                                    <span class="badge ${pl.bangLuong.trangThaiClass}">${pl.bangLuong.trangThaiLabel}</span>
                                    <c:if test="${not empty pl.bangLuong.ngayThanhToan}">
                                        <br><small style="color:var(--text-muted)">${pl.bangLuong.ngayThanhToan}</small>
                                    </c:if>
                                </td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${pl.bangLuong.trangThai.name() == 'DA_DUYET'}">
                                            <a href="${pageContext.request.contextPath}/thanh-toan?action=confirm&id=${pl.bangLuong.id}" class="btn btn-primary btn-sm">Chi Trả</a>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color:var(--text-muted); font-size: 0.8rem;">Đã Xong</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty dsPhieuLuong}">
                            <tr>
                                <td colspan="6" class="text-center" style="padding: 2rem;">Không có dữ liệu chờ thanh toán trong kỳ này.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>

                <div style="margin-top: 1.5rem;">
                    <button type="submit" id="btnPaySelected" class="btn btn-success" disabled>Thanh toán (0) mục đã chọn</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

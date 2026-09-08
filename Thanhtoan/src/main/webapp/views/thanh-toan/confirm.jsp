<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác Nhận Chi Trả - MixiMoi HRM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        .confirm-box { max-width: 500px; margin: 4rem auto; background: var(--bg-card); padding: 2.5rem; border-radius: var(--radius); border: 1px solid var(--border-color); text-align: center; box-shadow: var(--shadow); }
        .confirm-icon { font-size: 4rem; color: var(--warning); margin-bottom: 1rem; }
        .amount { font-size: 2.5rem; font-weight: 700; color: var(--success); margin: 1.5rem 0; }
        .nv-info { color: var(--text-muted); font-size: 1.1rem; margin-bottom: 2rem; }
    </style>
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

        <div class="confirm-box">
            <div class="confirm-icon">💸</div>
            <h2>Xác Nhận Chuyển Khoản Lương</h2>
            
            <div class="amount currency">
                <fmt:formatNumber value="${pl.bangLuong.luongThucLanh}" pattern="#,###"/>
            </div>
            
            <div class="nv-info">
                Tới: <strong>${pl.nhanVien.hoTen}</strong> (${pl.nhanVien.maNV})<br>
                Ngân hàng: <strong>Vietcombank - 0123456789</strong><br>
                Nội dung: TT Luong T${pl.bangLuong.thang}/${pl.bangLuong.nam} MixiMoi
            </div>

            <form method="post" action="${pageContext.request.contextPath}/thanh-toan" style="display: flex; gap: 1rem; justify-content: center;">
                <input type="hidden" name="action" value="xacnhan">
                <input type="hidden" name="id" value="${pl.bangLuong.id}">
                <input type="hidden" name="thang" value="${pl.bangLuong.thang}">
                <input type="hidden" name="nam" value="${pl.bangLuong.nam}">
                
                <a href="${pageContext.request.contextPath}/thanh-toan?thang=${pl.bangLuong.thang}&nam=${pl.bangLuong.nam}" class="btn btn-outline" style="padding: 0.75rem 2rem;">Hủy</a>
                <button type="submit" class="btn btn-primary" style="padding: 0.75rem 2rem;">Đồng Ý Chi Trả</button>
            </form>
        </div>
    </div>
</body>
</html>

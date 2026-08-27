<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${student != null ? 'Sửa sinh viên' : 'Thêm sinh viên'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>${student != null ? 'Sửa thông tin sinh viên' : 'Thêm sinh viên mới'}</h2>

        <form action="${pageContext.request.contextPath}/students" method="post">
            <input type="hidden" name="action" value="${student != null ? 'update' : 'add'}">

            <div class="form-group">
                <label>Mã sinh viên</label>
                <input type="text" name="id" class="form-control" required placeholder="Ví dụ: SV01" value="${student.id}" ${student != null ? 'readonly' : ''} style="${student != null ? 'background-color: #f3f4f6; color: #6b7280; cursor: not-allowed;' : ''}">
            </div>

            <div class="form-group">
                <label>Họ tên</label>
                <input type="text" name="name" class="form-control" required placeholder="Nhập họ và tên" value="${student.name}">
            </div>

            <div class="form-group">
                <label>Lớp</label>
                <input type="text" name="className" class="form-control" required placeholder="Ví dụ: IT1" value="${student.className}">
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" class="form-control" required placeholder="Nhập địa chỉ email" value="${student.email}">
            </div>

            <div class="flex-between mt-4">
                <a href="${pageContext.request.contextPath}/students" class="btn" style="background-color: transparent; color: var(--text-main); border: 1px solid var(--border);">Quay lại</a>
                <button type="submit" class="btn">Lưu sinh viên</button>
            </div>
        </form>
    </div>
</body>
</html>

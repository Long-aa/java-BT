<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Form sách</title></head>
<body>
<h2>Form sách</h2>
<form method="post" action="${pageContext.request.contextPath}/sach">
    <input type="hidden" name="id" value="${sach.id}">
    <p>Mã sách: <input name="maSach" value="${sach.maSach}" required></p>
    <p>Tên sách: <input name="tenSach" value="${sach.tenSach}" required></p>
    <p>Tác giả: <input name="tacGia" value="${sach.tacGia}"></p>
    <p>Nhà xuất bản: <input name="nhaXuatBan" value="${sach.nhaXuatBan}"></p>
    <p>Năm xuất bản: <input name="namXuatBan" type="number" value="${sach.namXuatBan}"></p>
    <button type="submit">Lưu</button>
</form>
<a href="${pageContext.request.contextPath}/sach">Quay lại danh sách</a>
</body>
</html>

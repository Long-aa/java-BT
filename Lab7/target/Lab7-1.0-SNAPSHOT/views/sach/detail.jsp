<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Chi tiết sách</title></head>
<body>
<h2>Chi tiết sách</h2>
<p>ID: ${sach.id}</p>
<p>Mã sách: ${sach.maSach}</p>
<p>Tên sách: ${sach.tenSach}</p>
<p>Tác giả: ${sach.tacGia}</p>
<p>Nhà xuất bản: ${sach.nhaXuatBan}</p>
<p>Năm xuất bản: ${sach.namXuatBan}</p>
<br>
<a href="${pageContext.request.contextPath}/sach">Quay lại danh sách</a>
</body>
</html>

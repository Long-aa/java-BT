<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Danh sách sách</title></head>
<body>
<h2>Danh sách sách</h2>
<form method="get" action="${pageContext.request.contextPath}/sach">
    <input name="keyword" placeholder="Tìm theo tên hoặc tác giả">
    <button type="submit">Tìm</button>
</form>
<p><a href="${pageContext.request.contextPath}/sach?action=new">Thêm sách</a></p>
<table border="1" cellpadding="6">
<tr><th>ID</th><th>Mã sách</th><th>Tên sách</th><th>Tác giả</th><th>Nhà xuất bản</th><th>Năm XB</th><th>Thao tác</th></tr>
<c:forEach var="sach" items="${dsSach}">
<tr>
    <td>${sach.id}</td><td>${sach.maSach}</td>
    <td><a href="${pageContext.request.contextPath}/sach?action=detail&id=${sach.id}">${sach.tenSach}</a></td>
    <td>${sach.tacGia}</td><td>${sach.nhaXuatBan}</td><td>${sach.namXuatBan}</td>
    <td>
        <a href="${pageContext.request.contextPath}/sach?action=edit&id=${sach.id}">Sửa</a> |
        <a href="${pageContext.request.contextPath}/sach?action=delete&id=${sach.id}" onclick="return confirm('Xóa?')">Xóa</a>
    </td>
</tr>
</c:forEach>
</table>
<br>
<a href="${pageContext.request.contextPath}/">Trang chủ</a>
</body>
</html>

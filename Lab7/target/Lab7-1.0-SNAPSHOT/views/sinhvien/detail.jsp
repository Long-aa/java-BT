<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Chi tiết sinh viên</title></head>
<body>
<h2>Chi tiết sinh viên</h2>
<p>ID: ${sv.id}</p>
<p>Mã SV: ${sv.maSinhVien}</p>
<p>Họ tên: ${sv.hoTen}</p>
<p>Email: ${sv.email}</p>
<p>Lớp: ${sv.lop}</p>
<br>
<a href="${pageContext.request.contextPath}/sinh-vien">Quay lại danh sách</a>
</body>
</html>

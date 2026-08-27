<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Danh sách sinh viên</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container container-large">
        <h2>Danh sách sinh viên</h2>

        <div class="action-bar">
            <c:if test="${sessionScope.role == 'admin'}">
                <a href="${pageContext.request.contextPath}/student-form.jsp" class="btn">+ Thêm sinh viên</a>
            </c:if>

            <form action="${pageContext.request.contextPath}/students" method="get" class="search-form">
                <input type="text" name="searchName" class="form-control" placeholder="Tìm kiếm theo họ tên" value="${searchQuery}" />
                <button type="submit" class="btn" style="background-color: var(--surface); color: var(--text-main); border: 1px solid var(--border);">Tìm kiếm</button>
            </form>
        </div>

        <c:choose>
            <c:when test="${empty students}">
                <div class="error-text" style="color: var(--text-muted); background: #f9fafb; border-color: var(--border);">
                    Không tìm thấy sinh viên nào phù hợp.
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-wrapper">
                    <table>
                        <tr>
                            <th>Mã SV</th>
                            <th>Họ tên</th>
                            <th>Lớp</th>
                            <th>Email</th>
                            <c:if test="${sessionScope.role == 'admin'}">
                                <th>Thao tác</th>
                            </c:if>
                        </tr>
                        <c:forEach var="sv" items="${students}">
                            <tr>
                                <td>${sv.id}</td>
                                <td>${sv.name}</td>
                                <td>${sv.className}</td>
                                <td>${sv.email}</td>
                                <c:if test="${sessionScope.role == 'admin'}">
                                    <td>
                                        <a href="${pageContext.request.contextPath}/students?action=edit&id=${sv.id}" class="btn" style="padding: 4px 8px; font-size: 0.85em; background: #e0e7ff; color: #4338ca;">Sửa</a>
                                        <a href="${pageContext.request.contextPath}/students?action=delete&id=${sv.id}" class="btn" style="padding: 4px 8px; font-size: 0.85em; background: #fee2e2; color: #b91c1c;" onclick="return confirm('Bạn có chắc chắn muốn xóa sinh viên này?');">Xóa</a>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/welcome.jsp" class="btn" style="background-color: transparent; color: var(--primary); padding: 0; text-decoration: underline;">&larr; Quay lại trang chủ</a>
        </div>
    </div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Trang quản trị - Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container container-large">
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <h2>Xin chào, ${sessionScope.username}</h2>
            <span style="color: var(--text-muted); font-size: 0.9rem;">
                Đăng nhập lúc: 
                <%
                    java.time.LocalDateTime loginTime = (java.time.LocalDateTime) session.getAttribute("loginTime");
                    if (loginTime != null) {
                        out.print(loginTime.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                    }
                %>
            </span>
        </div>
        
        <p class="mb-4" style="color: var(--text-muted);">Tổng quan hệ thống</p>
        
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 20px;">
            <div style="background: #f9fafb; padding: 20px; border-radius: 8px; border: 1px solid var(--border); text-align: center;">
                <h3 style="margin-top: 0; color: var(--text-muted);">Tổng số sinh viên</h3>
                <p style="font-size: 2.5rem; font-weight: bold; color: var(--primary); margin: 10px 0;">${totalStudents}</p>
            </div>
            
            <div style="background: #f9fafb; padding: 20px; border-radius: 8px; border: 1px solid var(--border);">
                <h3 style="margin-top: 0; color: var(--text-muted); text-align: center;">Sinh viên theo lớp</h3>
                <table style="width: 100%; border-collapse: collapse;">
                    <c:forEach var="entry" items="${classStats}">
                        <tr>
                            <td style="padding: 8px; border-bottom: 1px solid var(--border);">${entry.key}</td>
                            <td style="padding: 8px; border-bottom: 1px solid var(--border); text-align: right; font-weight: bold;">${entry.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <ul class="nav-links" style="display: flex; justify-content: center; gap: 20px;">
            <li><a href="${pageContext.request.contextPath}/students">Quản lý sinh viên</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
        </ul>
    </div>
</body>
</html>

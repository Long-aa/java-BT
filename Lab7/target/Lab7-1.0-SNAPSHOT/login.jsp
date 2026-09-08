<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String username = request.getParameter("username");
        if ("admin".equals(username)) {
            request.getSession().setAttribute("username", username);
            response.sendRedirect(request.getContextPath() + "/");
            return;
        } else {
            request.setAttribute("error", "Đăng nhập thất bại. Vui lòng dùng tài khoản 'admin'.");
        }
    }
%>
<html>
<head><title>Đăng nhập</title></head>
<body>
<h2>Đăng nhập</h2>
<p style="color:red">${error}</p>
<form method="post">
    <p>Username: <input name="username" required></p>
    <p>Password: <input type="password" name="password"></p>
    <button type="submit">Đăng nhập</button>
</form>
<a href="${pageContext.request.contextPath}/">Trang chủ</a>
</body>
</html>

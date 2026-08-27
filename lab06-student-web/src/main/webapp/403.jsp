<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access Denied - 403</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .error-page {
            text-align: center;
            padding: 50px 20px;
        }
        .error-code {
            font-size: 5rem;
            color: var(--primary);
            font-weight: bold;
            margin: 0;
        }
        .error-message {
            font-size: 1.5rem;
            color: var(--text-main);
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <div class="container container-large">
        <div class="error-page">
            <h1 class="error-code">403</h1>
            <p class="error-message">Bạn không có quyền truy cập vào chức năng này!</p>
            <p style="color: var(--text-muted); margin-bottom: 40px;">Chức năng này yêu cầu quyền Quản trị viên (Admin).</p>
            <a href="${pageContext.request.contextPath}/students" class="btn">Quay lại danh sách sinh viên</a>
        </div>
    </div>
</body>
</html>

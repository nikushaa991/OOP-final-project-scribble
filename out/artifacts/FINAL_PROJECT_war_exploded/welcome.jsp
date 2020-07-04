<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset=\"UTF-8\" />
    <title>Welcome <%= request.getParameter("username") %></title>
</head>
<body>
<h1>Welcome <%= request.getParameter("username") %></h1>
</body>
</html>

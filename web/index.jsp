<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="styles/index.css">
</head>
<body>
<div id="header">
    OOP
</div>
<div id="login">
    <form action="Login" method="POST">
        <div class="field">
            <input type="text" id="user" name="username" title="accountName" placeholder="Username" />
        </div>
        <div class="field">
            <input type="password" id="pass" name="password" title="accountPassword" placeholder="Password" />
        </div>
            <input type="submit" value="Login" id="log"/>
        </form>
            <input type="submit" onclick="window.location.href='new_user.jsp';" value="Registration" id="reg" />
</div>
</body>
</html>
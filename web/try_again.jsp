<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="styles/try-again.css">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/favicon.ico" type="image/x-icon">
</head>
<body>
<div id="header">
    OOP <img id="xvliki" src="xvliki.png">
</div>
<div id="login">
    <form action="Login" method="POST">
        <label id="lbl"> Access denied, try again. </label>
        <div class="field">
            <input type="text" id="user" name="username" title="accountName" placeholder="Username"/>
        </div>
        <div class="field">
            <input type="password" id="pass" name="password" title="accountPassword" placeholder="Password"/>
        </div>
        <input type="submit" value="Login" id="log"/>
    </form>
    <input type="submit" onclick="window.location.href='new_user.jsp';" value="Register" id="reg"/>
</div>
</body>
</html>
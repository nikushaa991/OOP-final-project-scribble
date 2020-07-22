<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="styles/user_exists.css">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/favicon.ico" type="image/x-icon">
</head>
<body>
<div id="header">
    OOP <img id="xvliki" src="xvliki.png">
</div>
<div id="login">
    <label id="lbl"> Username is taken, choose a different one. </label>
    <form action="UserCreated" method="POST">
        <div class="field">
            <input type="text" id="user" name="username" title="accountName" placeholder="Username"/>
        </div>
        <div class="field">
            <input type="password" id="pass" name="password" title="accountPassword" placeholder="Password"/>
        </div>
        <input type="submit" value="Register" id="reg"/>
    </form>
</div>
</body>
</html>
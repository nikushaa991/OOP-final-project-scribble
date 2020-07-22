<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="styles/new_user.css">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/favicon.ico" type="image/x-icon">
</head>
<body>
<div id="header">
    OOP <img id="xvliki" src="xvliki.png">
</div>
<div id="login">
    <form action="UserCreated" method="POST">
        <div class="field">
            <input type="text" id="user" name="username" title="accountName" placeholder="Username" minlength="3" required/>
        </div>
        <div class="field">
            <input type="password" id="pass" name="password" title="accountPassword" placeholder="Password" minlength="3" required/>
        </div>
        <input type="submit" value="Register" id="reg"/>
    </form>
</div>
</body>
</html>
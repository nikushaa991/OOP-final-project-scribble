<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="styles/new_user.css">
</head>
<body>
<div id="header">
    OOP
</div>
<div id="login">
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
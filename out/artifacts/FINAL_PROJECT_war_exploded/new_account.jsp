<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset=\"UTF-8\" />
    <title>Create Account</title>
</head>
<body>

<h1>Create New Account</h1>

Please enter proposed name and password
<form action="AccountCreated" method="POST">
    <label>User Name:</label>
    <input type="text" name="username" title="accountName"/>
    </br></br>
    <label>Password:</label>
    <input type="password" name="password" title="accountPassword"/>
    <input type="submit" value="Login"/>
</form>
</br>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset=\"UTF-8\" />
    <title>Create Account</title>
</head>
<body>

<h1>The Name <%= request.getParameter("username") %> is Already In Use</h1>

Please enter another name and password
<form action="UserCreated" method="POST">
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
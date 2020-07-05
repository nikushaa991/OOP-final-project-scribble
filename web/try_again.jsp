<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset=\"UTF-8\" />
    <title>Information Incorrect</title>
</head>
<body>

<h1>Please Try Again</h1>

Either your user name or password is incorrect. Please try again.
<form action="Login" method="POST">
    <label>User Name:</label>
    <input type="text" name="username" title="accountName"/>
    </br></br>
    <label>Password:</label>
    <input type="password" name="password" title="accountPassword"/>
    <input type="submit" value="Login"/>
</form>
</br>
<a href="new_user.jsp">Create New Account</a>
</body>
</html>
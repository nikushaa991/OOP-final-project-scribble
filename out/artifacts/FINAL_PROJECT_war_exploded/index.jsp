<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset=\"UTF-8\" />
  <title>Welcome</title>
</head>
<body>

<h1>Welcome to Scribble</h1>

Please Log in.
<form action="Login" method="POST">
  <label>User Name:</label>
  <input type="text" name="username" title="accountName"/>
  </br></br>
  <label>Password:</label>
  <input type="text" name="password" title="accountPassword"/>
  <input type="submit" value="Login"/>
</form>
</br>
<a href="new_account.jsp">Create New Account</a>
</body>
</html>
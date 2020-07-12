<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset=\"UTF-8\" />
    <title>Welcome</title>
</head>
<body>

<h1>HOME</h1>

<form action="QuickplayServlet" method="POST">
    <input type="submit" value="Casual play"/>
</form>

<form action="RankedplayServlet" method="POST">
    <input type="submit" value="Ranked play"/>
</form>

<form action="LeaderBoard" method="POST"> <%-- jerjerobit calke iyos da mere tu mogvinda amave gverdze gadmovitanot--%>
    <input type="submit" value="LeaderBoard"/>
</form>

<form action="FriendsList" method="POST"> <%-- jerjerobit calke iyos da mere tu mogvinda amave gverdze gadmovitanot--%>
    <input type="submit" value="FriendsList"/>
</form>

</body>
</html>
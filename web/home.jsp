<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset=\"UTF-8\" />
    <title>Home</title>
    <link rel="stylesheet" href="styles/home.css">
</head>
<body>
<div id="header">
    OOP
</div>

<div id="login">
<form action="QuickplayServlet" method="POST">
    <input type="submit" class="bttn" id="qp" value="Casual play"/>
</form>

<form action="RankedplayServlet" method="POST">
    <input type="submit" class="bttn" id="rp" value="Ranked play"/>
</form>

<form action="LeaderBoard" method="POST"> 
    <input type="submit" class="bttn" id="lb" value="Leaderboard"/>
</form>

<form action="FriendsList" method="POST"> 
    <input type="submit" class="bttn" id="fl" value="Friends"/>
</form>

</div>
</body>
</html>
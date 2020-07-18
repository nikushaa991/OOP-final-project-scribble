<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- need jstl:jstl:1.2 library for this (I got it from maven) --%>
<html>
<head>
    <meta charset=\"UTF-8\"/>
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

    <div id="leaderboard">
        <h1 class="txt">Leaderboard</h1>
        <div id="scroll-container">
            <ol id="ol-scroll">
                <c:forEach var="elem" items="${leaderboard}">
                    <li>
                        "${elem.getFirst()}" - "${elem.getSecond()}"
                    </li>
                </c:forEach>
            </ol>
        </div>
    </div>

    <form action="FriendsList" method="POST">
        <input type="submit" class="bttn" id="fl" value="Friends"/>
    </form>



</div>
</body>
</html>
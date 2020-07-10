<%--
  Created by IntelliJ IDEA.
  User: bubu
  Date: 09.07.20
  Time: 23:49
  To change this template use File | Settings | File Templates.
  NOTE: you need to have WEB-INF/lib/jstl-1.2.jar available or this .jsp will show error.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- need jstl:jstl:1.2 library for this (I got it from maven) --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Leaderboard </title>
</head>
<body>
<h1> TOP PLAYERS: </h1>
<ul>
    <c:forEach var="elem" items="${leaderboard}">
        <li>
            "${elem.getFirst()}"  -  "${elem.getSecond()}"
        </li>
    </c:forEach>
</ul>

</body>
</html>

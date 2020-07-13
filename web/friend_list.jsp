<%@ page import="java.util.ArrayList" %>
<%@ page import="game.classes.Game" %>
<%--
  Created by IntelliJ IDEA.
  User: bubu
  Date: 11.07.20
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<String> friendsList = (ArrayList<String>) request.getAttribute("friendsList");
    ArrayList<String> friendRequestsList = (ArrayList<String>) request.getAttribute("friendRequestsList");
    ArrayList<String> gameInvites = (ArrayList<String>) request.getAttribute("gameInvites");
    final int maxAdditionalPlayers = 100; // In reality it's a placeholder.
%>
<html>
<head>
    <script type="text/javascript"> <%-- Im not 100% sure this code should be here, but this function isn't called from any other file so im leaving it here for the time being --%>
        function checkBoxLimit(n_players) {
            var checkBoxGroup = document.forms['GameInvite']['tickInvite'];
            var limit = n_players;
            for (var i = 0; i < checkBoxGroup.length; i++) {
                checkBoxGroup[i].onclick = function() {
                    var checkedcount = 0;
                    for (var i = 0; i < checkBoxGroup.length; i++) {
                        checkedcount += (checkBoxGroup[i].checked) ? 1 : 0;
                    }
                    if (checkedcount > limit) {
                        alert("You can select maximum of " + limit + " checkboxes.");
                        this.checked = false;
                    }
                }
            }
        }
    </script>
    <title>Friend List</title>
</head>
<body>

<h2> Send friend request to: </h2>
<form action="FriendRequest" method="POST" name="FriendRequest">
    <input type="text" id="friendRequest" name="friendRequest">
    <input type="submit" value="Send"/>
</form>

<h2> Pending friend requests: </h2>
<ul style="list-style-type:none;">
    <form action="FriendRequestAccept" method="POST" name="FriendRequestAccept">
        <%
            for(String friendRequest : friendRequestsList){
                out.print("<li> <input type=\"checkbox\" name=\"accept\" value=" + friendRequest + " id = " + friendRequest + "</li>"
                        + "<label> " + friendRequest + " </label>");
            }
        %>
        </br></br>
        <input type="submit" value="Accept"/>
        </br></br>
    </form>
</ul>

<h2> <%= request.getAttribute("username") %>'s Friend list: </h2>

<ul  style="list-style-type:none;">
    <form action="GameInvite" method="POST" name="GameInvite">
        <%
            for(String friend : friendsList){
                out.print("<li> <input type=\"checkbox\"  name=\"tickInvite\" value=" + friend + " id = " + friend + "</li>"
                + "<label> " + friend + " </label>");
            }
        %>
        </br></br>
        <input type="submit" value="Invite to play" id="inviteToPlay" name="inviteToPlay"/>
        </br></br>
    </form>
    <script type="text/javascript">checkBoxLimit(<%=maxAdditionalPlayers%>)</script>
</ul>


<h2> Game invitations: </h2>
<%
    if(gameInvites != null)
    {
        out.print("<ul style=\"list-style-type:none;\"><form action=\"GameAccept\" method=\"POST\" name=\"GameAccept\">");
        for(String gameInvitation : gameInvites){
            out.print("<li> <input type=\"radio\" name=\"name\" value=" + gameInvitation + " id = \"name\"</li>"
                    + "<label> " + gameInvitation + " </label>");
        }
        out.print("</br></br>");
        out.print("<input type=\"submit\" value=\"Enter Room\"/>");
        out.print("</br></br>");
        out.print("</form>");
        out.print("</ul>");
    }
%>

</body>
</html>

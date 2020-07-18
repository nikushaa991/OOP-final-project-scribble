<%@ page import="java.util.ArrayList" %>
<%@ page import="game.classes.Game" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<String> friendsList = (ArrayList<String>) request.getAttribute("friendsList");
    ArrayList<String> friendRequestsList = (ArrayList<String>) request.getAttribute("friendRequestsList");
    ArrayList<String> gameInvites = (ArrayList<String>) request.getAttribute("gameInvites");
    final int maxAdditionalPlayers = 100; // In reality it's a placeholder.
%>
<html>

<head>
    <title>Friendlist</title>
    <link rel="stylesheet" href="styles/friend_list.css">
</head>

<body>

    <div id="header">
        OOP
    </div>

    <div id="login">

        <div id="newFriend">
            <form action="FriendRequest" method="POST" name="FriendRequest">
                <input type="text" id="friendRequest" name="friendRequest" placeholder="Enter friend name" />
                <br />
                <input type="submit" class="bttn" value="Send friend request" id="submitBttn" />
            </form>
        </div>

        <div id="friends">
            <h2 class="txt">Friends</h2>
            <form action="GameInvite" method="POST" name="GameInvite">
                <div class="scroll-container">
                    <ul class="ul-scroll">

                        <%
            for(String friend : friendsList){
                out.print("<li> <input type=\"checkbox\" class=\"item\" name=\"tickInvite\" value=" + friend + " id = " + friend + "</li>"
                + "<label> " + friend + " </label>");
            }
        %>


                    </ul>
                </div>
                <input type="submit" class="bttn" value="Invite to play" id="inviteToPlay" name="inviteToPlay" />
            </form>
        </div>
        <div id="invites">
            <h2 class="txt">Invites</h2>
            <form action="GameAccept" method="POST" name="GameAccept">
                <div class="scroll-container">
                    <ul class="ul-scroll">
                        <%
        if(gameInvites != null)
        for(String gameInvitation : gameInvites){
            out.print("<li> <input type=\"radio\" name=\"name\" value=" + gameInvitation + " id = \"name\"</li>"
                    + "<label> " + gameInvitation + " </label>");
        }
%>
                    </ul>
                </div>
                <input type="submit" class="bttn" value="Accept invite" id="inviteToPlay" name="inviteToPlay" />

            </form>
        </div>

        <div id="friendRequests">
            <h2 class="txt"> Friend Requests </h2>
            <ul class="ul-scroll">
                <form action="FriendRequestAccept" method="POST" name="FriendRequestAccept">
                    <div class="scroll-container">
                    <%
                        for(String friendRequest : friendRequestsList){
                            out.print("<li> <input type=\"checkbox\" name=\"accept\" value=" + friendRequest + " id = " + friendRequest + "</li>"
                                    + "<label> " + friendRequest + " </label>");
                        }
                    %>
                    </div>
                    <input type="submit" value="Accept" class ="bttn" id="friendAccept" />
                </form>
            </ul>
        </div>

    </div>
</body>

</html>
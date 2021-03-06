<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    ArrayList<String> friendsList = (ArrayList<String>) request.getAttribute("friendsList");
    ArrayList<String> friendRequestsList = (ArrayList<String>) request.getAttribute("friendRequestsList");
    ArrayList<String> gameInvites = (ArrayList<String>) request.getAttribute("gameInvites");
%>
<html>

<head>
    <title>Friendlist</title>
    <link rel="stylesheet" href="styles/friend_list.css">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/favicon.ico" type="image/x-icon">
</head>

<body>

<div id="header">
    OOP <img id="xvliki" src="xvliki.png">
</div>

<div id="login">

    <div id="newFriend">
        <form action="FriendRequest" method="POST" name="FriendRequest">
            <label for="friendRequest"></label><input type="text" id="friendRequest" name="friendRequest"
                                                      placeholder="Enter friend name"/>
            <br/>
            <input type="submit" class="bttn" value="Send friend request" id="submitBttn"/>
        </form>
    </div>

    <div id="friends">
        <h2 class="txt">Friends</h2>
        <form action="GameInvite" method="POST" name="GameInvite">
            <div class="scroll-container">
                <ul class="ul-scroll">

                    <%
                        for(String friend : friendsList)
                        {
                            out.print("<li> <input type=\"checkbox\" class=\"item\" name=\"tickInvite\" value=" + friend + " id = " + friend + "</li>"
                                    + "<label> " + friend + " </label>");
                        }
                    %>


                </ul>
            </div>
            <input type="submit" class="bttn" value="Invite to play" id="inviteToPlay1" name="inviteToPlay"/>
        </form>
    </div>
    <div id="invites">
        <h2 class="txt">Invites</h2>
        <form action="GameAccept" method="POST" name="GameAccept">
            <div class="scroll-container">
                <ul class="ul-scroll">
                    <%
                        if(gameInvites != null)
                            for(String gameInvitation : gameInvites)
                            {
                                out.print("<li> <input type=\"radio\" name=\"name\" value=" + gameInvitation + " id = \"name\"</li>"
                                        + "<label> " + gameInvitation + " </label>");
                            }
                    %>
                </ul>
            </div>
            <input type="submit" class="bttn" value="Accept invite" id="inviteToPlay2" name="inviteToPlay"/>

        </form>
    </div>

    <div id="friendRequests">
        <h2 class="txt"> Requests </h2>
        <form action="FriendRequestAccept" method="POST" name="FriendRequestAccept">
            <div class="scroll-container">
                <ul class="ul-scroll">
                    <div class="scroll-container">
                            <%
                                for(String friendRequest : friendRequestsList){
                                    out.print("<li> <input type=\"checkbox\" name=\"accept\" value=" + friendRequest + " id = " + friendRequest + "</li>"
                                            + "<label> " + friendRequest + " </label>");
                                }
                            %>
                </ul>
            </div>
            <input type="submit" value="Accept" class="bttn" id="friendAccept"/>
        </form>
    </div>

</div>
</body>

</html>
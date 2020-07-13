package home.servlets.friends;

import databases.friends.FriendRequestsDao;
import databases.friends.FriendsDAO;
import login.classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(value = "/FriendsList", name = "FriendsList")
public class FriendsListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> friendsList = null;
        ArrayList<String> friendRequestsList = null;
        FriendsDAO friendsDAO = (FriendsDAO) getServletContext().getAttribute("friends");
        FriendRequestsDao friendRequestsDao = (FriendRequestsDao)getServletContext().getAttribute("friendRequests");
        String username = ((User)request.getSession().getAttribute("USER")).getUsername();
        ConcurrentHashMap<String, ArrayList<String>> userInvites = (ConcurrentHashMap<String, ArrayList<String>>) getServletContext().getAttribute("gameInvites");
        ArrayList<String> gameInvites = userInvites.get(username);
        try {
            friendsList = friendsDAO.friendsList(username);
            friendRequestsList = friendRequestsDao.friendshipRequestsList(username);
        } catch (SQLException e) {  e.printStackTrace();  }
        request.setAttribute("friendsList", friendsList);
        request.setAttribute("friendRequestsList", friendRequestsList);
        request.setAttribute("username", username);
        request.setAttribute("gameInvites", gameInvites); // realurad shemidzlia egreve contextidan amovigo da gadacema agar mchirdeba
        request.getRequestDispatcher("friend_list.jsp").forward(request, response);
    }
}

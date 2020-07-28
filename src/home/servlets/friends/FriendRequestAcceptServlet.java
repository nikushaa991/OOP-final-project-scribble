package home.servlets.friends;

import databases.friends.FriendRequestsDao;
import databases.friends.FriendsDAO;
import login.classes.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/FriendRequestAccept", name = "FriendRequestAccept")
public class FriendRequestAcceptServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FriendsDAO friendsDao = (FriendsDAO) getServletContext().getAttribute("friends");
        FriendRequestsDao requestsDao = (FriendRequestsDao) getServletContext().getAttribute("friendRequests");
        String[] accepts = request.getParameterValues("accept");
        if(accepts != null)
        {
            String currentUser = ((User) request.getSession().getAttribute("USER")).getUsername();
            for(String friendRequest : accepts)
            {
                try
                {
                    requestsDao.delete(currentUser, friendRequest);
                    friendsDao.add(friendRequest, currentUser);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("FriendsList");
        rd.forward(request, response);
    }
}

package home.servlets.friends;

import databases.friends.FriendRequestsDao;
import login.classes.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/FriendRequest", name = "FriendRequest")
public class FriendRequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sendRequestTo = request.getParameter("friendRequest");
        FriendRequestsDao requestsDao = (FriendRequestsDao)getServletContext().getAttribute("friendRequests");
        try {
            requestsDao.newFriendshipRequest(sendRequestTo, ((User)request.getSession().getAttribute("USER")).getUsername());
        } catch (SQLException e) { e.printStackTrace(); }
        RequestDispatcher rd = request.getRequestDispatcher("FriendsList");
        rd.forward(request,response);
    }
}

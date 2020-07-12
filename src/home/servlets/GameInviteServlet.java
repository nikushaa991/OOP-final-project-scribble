package home.servlets;

import login.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(value = "/GameInvite", name = "GameInvite")
public class GameInviteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] invitedUsers = request.getParameterValues("tickInvite");
        if(invitedUsers != null) {
            String host = ((User) request.getSession().getAttribute("USER")).getUsername();
            ConcurrentHashMap<String, ArrayList<String>> userInvites = (ConcurrentHashMap<String, ArrayList<String>>) getServletContext().getAttribute("gameInvites");
            for (String invited : invitedUsers) {
                ArrayList<String> invites = userInvites.get(invited);
                if (invites == null) {
                    invites = new ArrayList<>();
                    userInvites.put(invited, invites);
                }
                invites.add(host);
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("QuickplayServlet"); // this should be changed probably??
        rd.forward(request, response);
    }
}

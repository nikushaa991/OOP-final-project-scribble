package home.servlets.games;

import databases.games.GamesDAO;
import databases.scores.ScoresDAO;
import game.classes.Game;
import login.classes.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(value = "/GameInvite", name = "GameInvite")
public class GameInviteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!(boolean) request.getSession().getAttribute("INGAME"))
        {
            ScoresDAO scoresDAO =  (ScoresDAO) getServletContext().getAttribute("scoresHistory");
            GamesDAO gamesDAO = (GamesDAO) getServletContext().getAttribute("gamesHistory");
            Game game = null;
            try {
                game = new Game(false, gamesDAO, scoresDAO);
            } catch (SQLException e) { e.printStackTrace(); }
            String host = ((User) request.getSession().getAttribute("USER")).getUsername();
            ((ConcurrentHashMap<String, Game>) getServletContext().getAttribute("HOSTED_GAMES")).put(host, game); //TODO: remove map entry if game terminated
            String[] invitedUsers = request.getParameterValues("tickInvite");
            if(invitedUsers != null)
            {
                ConcurrentHashMap<String, ArrayList<String>> userInvites = (ConcurrentHashMap<String, ArrayList<String>>) getServletContext().getAttribute("gameInvites");
                for(String invited : invitedUsers)
                {
                    ArrayList<String> invites = userInvites.get(invited);
                    if(invites == null)
                    {
                        invites = new ArrayList<>();
                        userInvites.put(invited, invites);
                    }
                    invites.add(host);
                }
            }
            HttpSession session = request.getSession();
            session.setAttribute("GAME", game);
        }
        RequestDispatcher rd = request.getRequestDispatcher("game.jsp"); // this should be changed probably??
        rd.forward(request, response);
    }
}

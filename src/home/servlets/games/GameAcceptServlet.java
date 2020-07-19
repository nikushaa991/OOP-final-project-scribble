package home.servlets.games;

import game.classes.Game;
import home.classes.Matchmaker;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(value = "/GameAccept", name = "GameAccept")
public class GameAcceptServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //redirect to proposed game.
        if(!(boolean) request.getSession().getAttribute("INGAME"))
        {
            String host = request.getParameter("name");
            Game game = ((ConcurrentHashMap<String, Game>) getServletContext().getAttribute("HOSTED_GAMES")).get(host);
            if(game.isFull())
            {
                Matchmaker mm = (Matchmaker) getServletContext().getAttribute("MATCHMAKER");
                try
                {
                    game = mm.addToQueue();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            HttpSession session = request.getSession();
            session.setAttribute("GAME", game);
        }
        RequestDispatcher rd = request.getRequestDispatcher("game.jsp");
        rd.forward(request, response);
    }
}

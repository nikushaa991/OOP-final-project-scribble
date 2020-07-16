package home.servlets.queue;

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

@WebServlet(value = "/QuickplayServlet", name = "QuickplayServlet")
public class QuickplayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(!(boolean) request.getSession().getAttribute("INGAME"))
        {
            Matchmaker mm = (Matchmaker) getServletContext().getAttribute("MATCHMAKER");
            Game game = null;
            try {
                game = mm.addToQueue();
            } catch (SQLException e) { e.printStackTrace(); }
            session.setAttribute("GAME", game);
        }

        RequestDispatcher rd = request.getRequestDispatcher("game.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

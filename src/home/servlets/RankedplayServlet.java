package home.servlets;

import game.Game;
import home.classes.Matchmaker;
import login.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/RankedplayServlet", name = "RankedplayServlet")
public class RankedplayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Matchmaker mm = (Matchmaker) getServletContext().getAttribute("MATCHMAKER");
        Game game = mm.addToRankedQueue(((User)session.getAttribute("USER")).getRating());
        session.setAttribute("GAME", game);
        RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

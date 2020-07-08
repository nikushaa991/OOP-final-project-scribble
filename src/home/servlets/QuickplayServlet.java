package home.servlets;

import game.Game;
import home.classes.Matchmaker;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/HomeServlet", name = "HomeServlet")

public class QuickplayServlet extends HttpServlet {
    static Game game = new Game();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Matchmaker mm = context.get(matchmaker);
        //Game game = mm.addToQueue();
        HttpSession session = request.getSession();
        session.setAttribute("GAME", game);
        RequestDispatcher rd = request.getRequestDispatcher("XATVA.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
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

@WebServlet(value = "/GameAccept", name = "GameAccept")
public class GameAcceptServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //redirect to proposed game.
        String username = ((User) request.getSession().getAttribute("USER")).getUsername();

    }
}

package login.servlets;

import databases.scores.ScoresDAO;
import databases.users.UsersDAO;
import utils.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(value = "/Login", name = "Login")
public class LoginServlet extends HttpServlet {
    private static final int topScoreCnt = 10;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersDAO userDb = (UsersDAO) getServletContext().getAttribute("users");
        String username = req.getParameter("username");
        String psw = req.getParameter("password");
        try {
            if(userDb.exists(username) && userDb.passwordMatches(username, psw)) {
                HttpSession session = req.getSession();
                session.setAttribute("USER", userDb.getUser(username));
                ScoresDAO scoresDb = (ScoresDAO) getServletContext().getAttribute("scoresHistory");
                ArrayList<Pair<String, Integer>> leaderboard = scoresDb.overallTopScores(topScoreCnt);
                getServletContext().setAttribute("leaderboard", leaderboard);
                req.getRequestDispatcher("home.jsp").forward(req, resp);
            } else
                req.getRequestDispatcher("try_again.jsp").forward(req, resp);
        } catch (SQLException | NoSuchAlgorithmException e) { e.printStackTrace(); }
    }
}

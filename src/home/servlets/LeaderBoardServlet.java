package home.servlets;

import databases.ScoresDAO;
import utils.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(value = "/LeaderBoard", name = "LeaderBoard")
public class LeaderBoardServlet extends HttpServlet{
    ArrayList<Pair<String, Integer>> leaderboard;
    private static final int topScoreCnt = 10;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ScoresDAO scoresDb = (ScoresDAO) getServletContext().getAttribute("scoresHistory");
        try {
            leaderboard = scoresDb.overallTopScores(topScoreCnt);
        } catch (SQLException e) {  e.printStackTrace();  }
        getServletContext().setAttribute("leaderboard", leaderboard);
        request.getRequestDispatcher("leaderboard.jsp").forward(request, response);
    }
}

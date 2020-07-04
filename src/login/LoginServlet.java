package login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(value = "/Login", name = "Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersDBConnector userDb = (UsersDBConnector) getServletContext().getAttribute("users");
        String username = req.getParameter("username");
        String psw = req.getParameter("password");
        try {
            if(userDb.exists(username) && userDb.passwordMatches(username, psw))
                req.getRequestDispatcher("welcome.jsp").forward(req, resp);
            else
                req.getRequestDispatcher("try_again.jsp").forward(req, resp);
        } catch (SQLException | NoSuchAlgorithmException e) { e.printStackTrace(); }
    }
}

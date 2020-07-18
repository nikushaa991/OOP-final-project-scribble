package login.servlets;

import databases.users.UsersDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(value = "/UserCreated", name = "UserCreated")
public class UserCreationServlet extends HttpServlet {
    private static final int START_RANKING = 1000;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersDAO userDb = (UsersDAO) getServletContext().getAttribute("users");
        String name = req.getParameter("username");
        String psw = req.getParameter("password");
        try {
            if(userDb.exists(name))
                req.getRequestDispatcher("user_exists.jsp").forward(req, resp);
            else {
                userDb.newUser(name, psw, START_RANKING);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

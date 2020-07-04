package main.java;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/AccountCreated", name = "AccountCreated")
public class AcctCreationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersDBConnector userDb = (UsersDBConnector) getServletContext().getAttribute("users");
        String name = req.getParameter("username");
        String psw = req.getParameter("password");
        try {
            if(userDb.exists(name))
                req.getRequestDispatcher("account_exists.jsp").forward(req, resp);
            else {
                userDb.newUser(name, psw);
                req.getRequestDispatcher("welcome.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

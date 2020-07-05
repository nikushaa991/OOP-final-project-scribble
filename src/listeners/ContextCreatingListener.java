package listeners;

import game.Game;
import login.UsersDBConnector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.ArrayList;

@WebListener
public class ContextCreatingListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        UsersDBConnector usersDb = null;
        ArrayList<Game> currentGames = new ArrayList<>();
        try {
            usersDb = new UsersDBConnector();
        } catch (SQLException e) { e.printStackTrace(); }
        servletContextEvent.getServletContext().setAttribute("users", usersDb);
        servletContextEvent.getServletContext().setAttribute("games", usersDb);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        UsersDBConnector usersDb = (UsersDBConnector) servletContextEvent.getServletContext().getAttribute("users");
        try {
            usersDb.closeConnection();
        } catch (SQLException e) { e.printStackTrace(); }
        System.out.println("System undeployed.");
    }
}

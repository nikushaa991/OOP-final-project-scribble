package listeners;

import databases.ScoresDBConnector;
import game.Game;
import game.GameDBConnector;
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
        ScoresDBConnector scoresDb = null;
        GameDBConnector gameDb = null;
        ArrayList<Game> currentGames = new ArrayList<>();
        try {
            usersDb = new UsersDBConnector();
            scoresDb =  new ScoresDBConnector();
            gameDb =  new GameDBConnector();
        } catch (SQLException e) { e.printStackTrace(); }
        servletContextEvent.getServletContext().setAttribute("users", usersDb);
        servletContextEvent.getServletContext().setAttribute("scoresHistory", scoresDb);
        servletContextEvent.getServletContext().setAttribute("gamesHistory", gameDb);
        servletContextEvent.getServletContext().setAttribute("games", currentGames);
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

package listeners;

import databases.ScoresDAO;
import game.Game;
import game.GamesDAO;
import login.UsersDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.ArrayList;

@WebListener
public class ContextCreatingListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //TODO: store matchmaker in context.
        UsersDAO usersDb = null;
        ScoresDAO scoresDb = null;
        GamesDAO gameDb = null;
        ArrayList<Game> currentGames = new ArrayList<>();
        try {
            usersDb = new UsersDAO();
            scoresDb =  new ScoresDAO();
            gameDb =  new GamesDAO();
        } catch (SQLException e) { e.printStackTrace(); }
        servletContextEvent.getServletContext().setAttribute("users", usersDb);
        servletContextEvent.getServletContext().setAttribute("scoresHistory", scoresDb);
        servletContextEvent.getServletContext().setAttribute("gamesHistory", gameDb);
        servletContextEvent.getServletContext().setAttribute("games", currentGames);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        UsersDAO usersDb = (UsersDAO) servletContextEvent.getServletContext().getAttribute("users");
        try {
            usersDb.closeConnection();
        } catch (SQLException e) { e.printStackTrace(); }
        System.out.println("System undeployed.");
    }
}

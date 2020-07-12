package listeners;

import databases.FriendRequestsDao;
import databases.FriendsDAO;
import databases.ScoresDAO;
import game.Game;
import game.GamesDAO;
import home.classes.Matchmaker;
import login.UsersDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class ContextCreatingListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //TODO: store matchmaker in context.
        UsersDAO usersDb = null;
        ScoresDAO scoresDb = null;
        GamesDAO gameDb = null;
        FriendsDAO friendsDAO = null;
        FriendRequestsDao friendRequestsDao = null;
        ArrayList<Game> currentGames = new ArrayList<>();
        Matchmaker mm = new Matchmaker();
        ConcurrentHashMap<String, ArrayList<String> > gameInvites = new ConcurrentHashMap<>();
        try {
            usersDb = new UsersDAO();
            scoresDb =  new ScoresDAO();
            gameDb =  new GamesDAO();
            friendsDAO = new FriendsDAO();
            friendRequestsDao = new FriendRequestsDao();
        } catch (SQLException e) { e.printStackTrace(); }
        servletContextEvent.getServletContext().setAttribute("users", usersDb);
        servletContextEvent.getServletContext().setAttribute("scoresHistory", scoresDb);
        servletContextEvent.getServletContext().setAttribute("gamesHistory", gameDb);
        servletContextEvent.getServletContext().setAttribute("games", currentGames);
        servletContextEvent.getServletContext().setAttribute("friends", friendsDAO);
        servletContextEvent.getServletContext().setAttribute("friendRequests", friendRequestsDao);
        servletContextEvent.getServletContext().setAttribute("gameInvites", gameInvites);
        servletContextEvent.getServletContext().setAttribute("MATCHMAKER", mm);
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

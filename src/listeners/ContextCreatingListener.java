package listeners;

import login.UsersDBConnector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextCreatingListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        UsersDBConnector usersDb = new UsersDBConnector();
        servletContextEvent.getServletContext().setAttribute("users", usersDb);
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

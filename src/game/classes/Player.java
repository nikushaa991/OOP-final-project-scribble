package game.classes;

import databases.users.UsersDAO;
import login.classes.User;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;


public class Player {
    private int score;
    private String name;
    private User user;
    private Session wsSession;
    private boolean bCanGuess;
    private UsersDAO usersDAO;

    public Player(Session wsSession, User user, UsersDAO usersDAO) {
        this.user = user;
        this.name = user.getUsername();
        this.wsSession = wsSession;
        this.usersDAO = usersDAO;
        score = 0;
    }

    /* Getters */
    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    //TODO: maybe do something with these two methods
    public boolean getCanGuess() {
        return bCanGuess;
    }

    public Session getSession() {
        return wsSession;
    }
    /* Setters */

    public void increaseScore(int Score) {
        score += Score;
    }

    public void setCanGuess(boolean b) {
        bCanGuess = b;
    }


    public void notifyPlayer(String text)
    {
        try
        {
            wsSession.getBasicRemote().sendText(text);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setSession(Session session) {
        wsSession = session;
    }

    public void UpdateRank(int score) throws SQLException {
        user.changeRating(score, usersDAO);
    }
}
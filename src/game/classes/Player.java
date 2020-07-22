package game.classes;

import databases.users.UsersDAO;
import login.classes.User;

import javax.websocket.Session;
import java.sql.SQLException;


public class Player {
    private final String name;
    private final User user;
    private final UsersDAO usersDAO;
    private int score;
    private Session wsSession;
    private boolean bCanGuess;

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

    public boolean getCanGuess() {
        return bCanGuess;
    }

    public void setCanGuess(boolean b) {
        if(!b) this.notifyPlayer("D,");
        bCanGuess = b;
    }

    public void increaseScore(int Score) {
        score += Score;
    }

    public void notifyPlayer(String text) {
        try
        {
            wsSession.getBasicRemote().sendText(text);
        } catch (Exception e)
        {
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
package game.classes;

import login.classes.User;

import javax.websocket.Session;
import java.io.IOException;


public class Player
{
    private int score;
    private String name;
    private Session wsSession;
    private boolean bCanGuess;

    public Player(Session wsSession, User user) {

        this.name = user.getUsername();
        this.wsSession = wsSession;
        score = 0;
    }

    /* Getters */
    public int getScore()
    {
        return score;
    }

    public String getName()
    {
        return name;
    }

    //TODO: maybe do something with these two methods
    public boolean getCanGuess()
    {
        return bCanGuess;
    }

    public Session getSession(){return wsSession;}
    /* Setters */

    public void increaseScore(int Score)
    {
        score += Score;
    }

    public void setCanGuess(boolean b)
    {
        bCanGuess = b;
    }



    public void notifyPlayer(String text) throws IOException //TODO: move this to a negotiator class instead.
    {
        wsSession.getBasicRemote().sendText(text);
    }

}
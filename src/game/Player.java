package game;

import javax.websocket.Session;
import java.io.IOException;

public class Player
{

    private int score;
    private int order;
    private String name;
    private Session session;

    public Player(int i, int order, Session session) {
        this.order = order;
        this.session = session;
        //this.name = session.user.getName();
        score = 0;
    }

    /* Getters */
    public int GetScore()
    {
        return score;
    }

    public String GetName()
    {
        return name;
    }

    public int GetOrder()
    {
        return order;
    }

    public Session getSession(){return session;}
    /* Setters */

    public void IncreaseScore(int Score)
    {
        score += Score;
    }

    public void TryGuess(String guess)
    {
        Round CurrentRound = null;
        int result = CurrentRound.CheckGuess(guess);
    }

    public void notifyPlayer(String text) throws IOException
    {
        session.getBasicRemote().sendText(text);
    }

}
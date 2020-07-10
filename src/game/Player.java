package game;

import login.User;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;


public class Player
{
    private int score;
    private String name;
    private Session session;
    private boolean bCanGuess;

    public Player(Session session, User user) {

        this.name = user.getUsername();
        this.session = session;

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


    public boolean GetCanGuess()
    {
        return bCanGuess;
    }


    public Session getSession(){return session;}
    /* Setters */

    public void IncreaseScore(int Score)
    {
        score += Score;
    }

    public void SetCanGuess(boolean b)
    {
        bCanGuess = b;
    }



    public void notifyPlayer(String text) throws IOException
    {
        session.getBasicRemote().sendText(text);
    }

}
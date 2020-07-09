package game;

import login.User;
import main.java.Pair;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


//SEND TEXT TO PLAYER USING THIS!!!!!!!
//session.getBasicRemote().sendText(text);


public class Game {
    public static final int N_ROUNDS = 18;
    public static final int MAX_PLAYERS = 6;

    private int playerCount;
    private int curRound;
    private String word;
    private Round[] rounds;
    private Player[] players;

    public Game() {
        players = new Player[MAX_PLAYERS];
        rounds = new Round[N_ROUNDS];
        playerCount = 0;
        curRound = 0;
        word = "";
    }

    public synchronized void registerSession(Session session) throws IOException, InterruptedException {
        Player newPlayer = new Player(0, playerCount, session);
        players[playerCount] = newPlayer;
        playerCount++;
        if(playerCount == 2)
            begin();
    }

    private void begin() throws IOException, InterruptedException {
        for(; curRound < N_ROUNDS; curRound++)
        {
            rounds[curRound] = new Round(players[curRound % playerCount].getSession());
            Round CurrentRound = rounds[curRound];
            CurrentRound.OnRoundBegin();
            // guessers have the ability to guess the word in chat
            // painter has the ability to paint the hidden word in canvas
            // After timer ends, or everyone guesses
            CurrentRound.OnRoundEnd();
        }
        GetWinner();
    }


    private void GetWinner() {
        Player winner = null;
        int maxScore = 0;
        for(Player p : players)
        {
            if(p.GetScore() > maxScore)
            {
                maxScore = p.GetScore();
                winner = p;
            }
        }

        // Debug winner won the game
    }

    public void stroke(String start) throws IOException {
        for(int i = 0; i < playerCount; i++)
            players[i].getSession().getBasicRemote().sendText(start);

    }
}
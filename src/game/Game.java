package game;

import javax.websocket.Session;


//SEND TEXT TO PLAYER USING THIS!!!!!!!
//session.getBasicRemote().sendText(text);


public class Game {
    public static final int N_ROUNDS = 18;
    public static final int MAX_PLAYERS = 6;

    private int playerCount;
    private int curRound;
    private String word;
    private Round[] rounds;
    private Player[] Players;

    public Game() {
        Players = new Player[MAX_PLAYERS];
        rounds = new Round[N_ROUNDS];
        playerCount = 0;
        curRound = 0;
        word = "";
    }

    public synchronized void registerSession(Session session) {
        Player newPlayer = new Player(/*session.id*/ 0, playerCount, session);
        Players[playerCount] = newPlayer;
        playerCount++;
        if(playerCount == 2)
            begin();
    }

    private void begin() {
        for(; curRound < N_ROUNDS; curRound++)
        {
            rounds[curRound] = new Round(null);
            Round CurrentRound = rounds[curRound];
            CurrentRound.OnRoundBegin();
            // guessers have the ability to guess the word in chat
            // painter has the ability to paint the hidden word in canvas
            // After timer ends, or everyone guesses
            CurrentRound.OnRoundEnd();
        }
        GetWinner();
    }


    private void GetWinner()
    {
        Player winner = null;
        int maxScore = 0;
        for(Player p : Players)
        {
            if(p.GetScore() > maxScore)
            {
                maxScore = p.GetScore();
                winner = p;
            }
        }

        // Debug winner won the game
    }

}

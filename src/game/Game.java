package game;

import login.User;


import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//SEND TEXT TO PLAYER USING THIS!!!!!!!
//session.getBasicRemote().sendText(text);


public class Game {
    public static final int N_ROUNDS = 18;
    public static final int MAX_PLAYERS = 6;

    private int playerCount;
    private int curRound;
    private Round[] rounds;
    GamesDAO dao;
    private Player[] players;
    private boolean ranked;

    public Game(boolean ranked, GamesDAO dao) {

        players = new Player[MAX_PLAYERS];
        rounds = new Round[N_ROUNDS];
        playerCount = 0;
        curRound = 0;
        this.ranked = ranked;
        this.dao = dao;
    }

    public synchronized int registerSession(Session session, User user) {
        Player newPlayer = new Player(session, user);
        players[playerCount] = newPlayer;
        playerCount++;
        if(playerCount == 2)
        {
            //TODO: make this look prettier
            new Thread(() -> {
                try
                {
                    begin();
                } catch (IOException e) {e.printStackTrace();}
                catch (InterruptedException | SQLException e) {e.printStackTrace();}
            }).start();
        }
        return playerCount - 1;
    }

    private void begin() throws IOException, InterruptedException, SQLException {
        for(; curRound < N_ROUNDS; curRound++)
        {
            rounds[curRound] = new Round(players[curRound % playerCount]);
            Round CurrentRound = rounds[curRound];

            CurrentRound.OnRoundBegin(players);
            // Game in Progress
            CurrentRound.OnRoundEnd(players);
        }
        Player p = GetWinner(); //TODO: increase winner rating if ranked, game should store if it's ranked or not
        //TODO: store game in database, including all rounds
        updateDatabase(p);
    }

    /* Writes new game entry after a game ends by
    * accessing the context attribute DAO and saving new entry.
    * */
    private void updateDatabase(Player winner) throws SQLException {
        dao.newGame(ranked, winner.getName(), winner.getScore());
    }

    private Player GetWinner() {
        Player winner = null;
        int maxScore = 0;
        for(Player p : players)
        {
            if(p != null && p.getScore() > maxScore)
            {
                maxScore = p.getScore();
                winner = p;
            }
        }
        return winner;

        // Debug winner won the game
    }

    //TODO: store all strokes to store in database, for live replay of drawing.
    //TODO: handle colors and sizes.
    public void stroke(String start, int id) throws IOException {
        for(int i = 0; i < playerCount; i++) //TODO: this should be a separate method in a negotiator class as notifyAllExceptOne()
            if(players[i] != null && i != id)
                players[i].notifyPlayer(start);
    }

    public void CheckGuessFromGame(int PlayerIndex, String guess) throws IOException {
        if(playerCount < 2 || curRound == N_ROUNDS) //TODO: make this prettier, sentinel instead of 18
        {
            for(Player p : players)
                if(p != null)
                    p.notifyPlayer("C," + players[PlayerIndex].getName() + ": " + guess);
            return;
        }
        Round round = rounds[curRound];
        int res = round.CheckGuess(guess);
        //TODO: make ifs prettier, enum maybe
        if(res == 1)
        {
            round.OnCorrectGuess(players, PlayerIndex);
        }
        else if(res == 2) //TODO: implement this
        {
            round.OnCloseGuess(players[PlayerIndex]);
        }
        else
        {
            round.OnIncorrectGuess(players, PlayerIndex, guess);
        }
    }
    //TODO: implement in a better way so disconnected player can't be null
    public synchronized void unregister(int playerIndex){
        players[playerIndex] = null;
        playerCount--;
    }
}
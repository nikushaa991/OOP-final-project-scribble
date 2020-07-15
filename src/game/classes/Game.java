package game.classes;

import databases.games.GamesDAO;
import login.classes.User;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

//SEND TEXT TO PLAYER USING THIS!!!!!!!
//session.getBasicRemote().sendText(text);


public class Game {
    public static final int N_ROUNDS = 18;
    public static final int MAX_PLAYERS = 6;

    private int registeredPlayers;
    private int activePlayerCount;
    private int curRound;
    private boolean ranked;
    private GamesDAO dao;
    private Round[] rounds;
    private Player[] players; //TODO: create better structure for this, accommodate for disconnects and painter queue.
    private boolean[] isActive;
    private ArrayList<String> instructions;
    private int painterId;
    public Game(boolean ranked, GamesDAO dao) {

        players = new Player[MAX_PLAYERS];
        rounds = new Round[N_ROUNDS];
        isActive = new boolean[MAX_PLAYERS];
        activePlayerCount = 0;
        registeredPlayers = 0;
        curRound = 0;
        this.ranked = ranked;
        this.dao = dao;
        instructions = new ArrayList<>();
        painterId = 0;
    }

    public void reconnect(int id, Session session) throws IOException {
        players[id].setSession(session);
        isActive[id] = true;
        players[id].notifyPlayer("M,Reconnected to an old game!");
        activePlayerCount++;
        catchup(id);
    }

    public synchronized int registerSession(Session session, User user) throws IOException {
        Player newPlayer = new Player(session, user);
        players[activePlayerCount] = newPlayer;
        isActive[activePlayerCount] = true;
        activePlayerCount++;
        registeredPlayers++;
        if(activePlayerCount == 2)
        {
            //TODO: make this look prettier
            new Thread(() -> {
                try
                {
                    begin();
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (InterruptedException | SQLException e)
                {
                    e.printStackTrace();
                }
            }).start();
        }
        else if (activePlayerCount > 2) catchup(activePlayerCount-1);
        return activePlayerCount - 1;
    }

    private void begin() throws IOException, InterruptedException, SQLException {
        for(int painterNum = 0; curRound < N_ROUNDS; curRound++, painterNum++)
        {
            if(activePlayerCount == 0)
                return;
            while (!isActive[painterNum % MAX_PLAYERS])
                painterNum++;
            painterId = painterNum % MAX_PLAYERS;
            rounds[curRound] = new Round(players[painterId]);
            Round CurrentRound = rounds[curRound];
            instructions.clear();
            CurrentRound.OnRoundBegin(players, isActive);
            // Game in Progress
            CurrentRound.OnRoundEnd(players, isActive);
        }
        Player p = GetWinner(); //TODO: increase winner rating if ranked, game should store if it's ranked or not
        //TODO: store game in database, including all rounds
        //updateDatabase(p);
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
        for(int i = 0; i < MAX_PLAYERS; i++)
        {
            Player p = players[i];
            if(isActive[i] && p.getScore() > maxScore)
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
        instructions.add(start);
        for(int i = 0; i < MAX_PLAYERS; i++) //TODO: this should be a separate method in a negotiator class as notifyAllExceptOne()
            if(isActive[i] && i != id)
                players[i].notifyPlayer(start);
    }

    public void CheckGuessFromGame(int PlayerIndex, String guess) throws IOException {
        if(!players[PlayerIndex].getCanGuess())
            return;
        if(registeredPlayers < 2 || curRound == N_ROUNDS) //TODO: make this prettier, sentinel instead of 18
        {
            for(int i = 0; i < MAX_PLAYERS; i++) //TODO: this should be a separate method in a negotiator class as notifyAllExceptOne()
                if(isActive[i])
                    players[i].notifyPlayer("C," + players[PlayerIndex].getName() + ": " + guess);
            return;
        }
        Round round = rounds[curRound];
        if(round.CheckGuess(guess)) //TODO: capitalization shouldn't matter
            round.OnCorrectGuess(players, isActive,PlayerIndex);
         else
            round.OnIncorrectGuess(players, isActive,PlayerIndex, guess);
    }

    //TODO: implement in a better way so disconnected player can't be null
    public synchronized void unregister(int playerIndex) {
        isActive[playerIndex] = false;
        activePlayerCount--;
    }

    public synchronized void SetHiddenWord(String word) {
        rounds[curRound].ChooseHiddenWord(word);
    }

    public synchronized int getActivePlayerCount() {
        return activePlayerCount;
    }

    public synchronized int getRegisteredPlayers() {
        return registeredPlayers;
    }

    private void catchup(int id) throws IOException {
        for(String s : instructions)
            players[id].notifyPlayer(s);
        if(id == painterId)
            players[id].notifyPlayer("P,");

    }
}
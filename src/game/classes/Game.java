package game.classes;

import databases.games.GamesDAO;
import databases.scores.ScoresDAO;
import databases.users.UsersDAO;
import login.classes.User;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

//SEND TEXT TO PLAYER USING THIS!!!!!!!
//session.getBasicRemote().sendText(text);


public class Game {
    public static final int N_ROUNDS = 2;
    public static final int MAX_PLAYERS = 6;

    private int registeredPlayers;
    private int activePlayerCount;
    private int curRound;
    private boolean ranked;
    private Round[] rounds;
    private Player[] players; //TODO: create better structure for this, accommodate for disconnects and painter queue.
    private boolean[] isActive;
    private ArrayList<String> instructions;
    private int painterId;

    private GamesDAO gamesDAO;
    private ScoresDAO scoresDAO;
    private UsersDAO usersDAO;
    private int gameId;
    public Game(boolean ranked, GamesDAO gamesDAO, ScoresDAO scoresDAO, UsersDAO usersDAO) throws SQLException {

        players = new Player[MAX_PLAYERS];
        rounds = new Round[N_ROUNDS];
        isActive = new boolean[MAX_PLAYERS];
        activePlayerCount = 0;
        registeredPlayers = 0;
        curRound = 0;
        this.ranked = ranked;
        instructions = new ArrayList<>();
        painterId = 0;
        this.gamesDAO = gamesDAO;
        this.scoresDAO = scoresDAO;
        this.usersDAO = usersDAO;
        gameId = gamesDAO.newGame(ranked);
    }

    public void reconnect(int id, Session session) throws IOException {
        players[id].setSession(session);
        isActive[id] = true;
        players[id].notifyPlayer("M,Reconnected to an old game!");
        activePlayerCount++;
        catchup(id);
    }

    public synchronized int registerSession(Session session, User user) throws IOException {
        Player newPlayer = new Player(session, user, usersDAO);
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
            rounds[curRound] = new Round(players[painterId], gameId, scoresDAO, curRound);
            Round CurrentRound = rounds[curRound];
            instructions.clear();
            CurrentRound.OnRoundBegin(players, isActive);
            // Game in Progress
            CurrentRound.OnRoundEnd(players, isActive);
        }
        Player p = GetWinner();
        for(int i = 0; i < Game.MAX_PLAYERS; i++)
            if(isActive[i])
            {
                players[i].notifyPlayer("N,");
                players[i].notifyPlayer("M," + p.getName() + " has won the game!");
            }
        if(ranked)
            UpdatePlayerRanks();
        updateGamesDAO(p);
    }


    //TODO: Test if this works
    void UpdatePlayerRanks() throws SQLException {
        // sort the players
        for(int i = 0; i < players.length; i++)
        {
            if(players[i] == null) continue;
            int min = i;
            for(int j = i + 1; j < players.length; j++)
            {
                if(players[j] == null) continue;

                if(players[j].getScore() < players[min].getScore())
                    min = j;
            }

            Player tmp = players[min];
            players[min] = players[i];
            players[i] = tmp;
        }
        // Update scores
        int rankScore = 30;
        int increment = 60/getRegisteredPlayers();
        for (Player p : players) {
            if (p != null)
            {
                p.UpdateRank(rankScore);
                rankScore -= increment;
                if (rankScore == 0) rankScore -= increment;
            }
        }

    }

    /* Writes new game entry after a game ends by
     * accessing the context attribute DAO and saving new entry.
     * */
    private void updateGamesDAO(Player winner) throws SQLException {
        if(winner != null)
            gamesDAO.updateGame(gameId, winner.getName(), winner.getScore());
    }

    private Player GetWinner() {
        Player winner = null;
        int maxScore = -5;
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

    public void CheckGuessFromGame(int PlayerIndex, String guess) throws IOException, SQLException {
        if(!players[PlayerIndex].getCanGuess())
            return;
        if(registeredPlayers < 2 || curRound == N_ROUNDS)
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
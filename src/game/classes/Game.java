package game.classes;

import databases.games.GamesDAO;
import databases.scores.ScoresDAO;
import databases.users.UsersDAO;
import login.classes.User;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Game {
    public static final int N_ROUNDS = 2;
    public static final int MAX_PLAYERS = 6;
    public static final String[] colors = {"#98fb98", "#6495ed", "#9370db", "#dda0dd", "#27927", "#e2a50e"};

    private int registeredPlayers;
    private int activePlayerCount;
    private int curRound;
    private int painterId;
    private boolean ranked;
    private Round[] rounds;
    private Player[] players;
    private boolean[] isActive;
    private ArrayList<String> instructions;
    private boolean isOver;

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
        painterId = 0;
        isOver = false;
        instructions = new ArrayList<>();
        this.ranked = ranked;
        this.gamesDAO = gamesDAO;
        this.scoresDAO = scoresDAO;
        this.usersDAO = usersDAO;
        gameId = gamesDAO.newGame(ranked);
    }

    public void reconnect(int id, Session session) throws IOException {
        players[id].setSession(session);
        isActive[id] = true;
        activePlayerCount++;
        players[id].notifyPlayer("M,Reconnected to an old game!");
        catchup(id);
    }

    public synchronized int registerSession(Session session, User user) throws IOException {
        Player newPlayer = new Player(session, user, usersDAO);
        players[registeredPlayers] = newPlayer;
        isActive[registeredPlayers] = true;
        activePlayerCount++;
        registeredPlayers++;
        if(registeredPlayers == 2)
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
        } else if(registeredPlayers > 2) catchup(registeredPlayers - 1);
        return registeredPlayers - 1;
    }

    private void begin() throws IOException, InterruptedException, SQLException
    {
        for(int painterNum = 0; curRound < N_ROUNDS; curRound++, painterNum++)
        {
            if(activePlayerCount == 0)
            {
                isOver = true;
                return;
            }
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
        isOver = true;
        painterId = -1;
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


    /*
     * Runs trough all the players, sorts them and Updates each Ranking based on players place
     */
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
        int increment = 60 / registeredPlayers;
        for(Player p : players)
        {
            if(p != null)
            {
                p.UpdateRank(rankScore);
                rankScore -= increment;
                if(rankScore == 0) rankScore -= increment;
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
    }

    public void stroke(String start, int id) throws IOException {
        instructions.add(start);
        for(int i = 0; i < MAX_PLAYERS; i++)
            if(isActive[i] && i != id)
                players[i].notifyPlayer(start);
    }

    public void CheckGuessFromGame(int PlayerIndex, String guess) throws IOException, SQLException {
        if(registeredPlayers < 2 || curRound == N_ROUNDS)
        {
            for(int i = 0; i < MAX_PLAYERS; i++)
                if(isActive[i])
                    players[i].notifyPlayer("C," + Game.colors[i] + ',' + players[PlayerIndex].getName() + "," + guess);
            return;
        }
        if(!players[PlayerIndex].getCanGuess())
            return;
        Round round = rounds[curRound];
        if(round.CheckGuess(guess))
            round.OnCorrectGuess(players, isActive, PlayerIndex);
        else
            round.OnIncorrectGuess(players, isActive, PlayerIndex, guess);
    }

    public synchronized void unregister(int playerIndex) {
        isActive[playerIndex] = false;
        activePlayerCount--;
    }

    public synchronized void SetHiddenWord(String word) {
        rounds[curRound].ChooseHiddenWord(word);
    }

    public synchronized boolean isOver() {
        return isOver;
    }

    private void catchup(int id) throws IOException {
        for(String s : instructions)
            players[id].notifyPlayer(s);
        if(id == painterId)
            players[id].notifyPlayer("P,");
    }

}
package home.classes;

import databases.games.GamesDAO;
import databases.scores.ScoresDAO;
import game.classes.Game;

import java.sql.SQLException;

public class Matchmaker {
    private Game game;
    private Game rankedGames[];
    private int inQueue;
    private int rankedQueue[];
    private GamesDAO gamesDAO;
    private ScoresDAO scoresDAO;

    public Matchmaker(GamesDAO gamesDAO, ScoresDAO scoresDAO) throws SQLException {
        this.gamesDAO = gamesDAO;
        this.scoresDAO = scoresDAO;
        game = new Game(false, gamesDAO, scoresDAO);
        inQueue = 0;

        rankedGames = new Game[10];
        rankedQueue = new int[10];
        for(int i = 0; i < 10; i++)
        {
            rankedGames[i] = new Game(true, gamesDAO, scoresDAO);
            rankedQueue[i] = 0;
        }
    }

    synchronized public Game addToQueue() throws SQLException {
        if(game.getRegisteredPlayers() == 0)
        {
            game = new Game(false, gamesDAO, scoresDAO);
            inQueue = 1;
        } else if(++inQueue == 6)
        {
            Game res = game;
            game = new Game(false, gamesDAO, scoresDAO);
            inQueue = 0;
            return res;
        }
        return game;
    }

    synchronized public Game addToRankedQueue(int rating) throws SQLException {
        int bracket = rating / 300;

        if(rankedGames[bracket].getRegisteredPlayers() == 0)
        {
            rankedGames[bracket] = new Game(true, gamesDAO, scoresDAO);
            rankedQueue[bracket] = 1;
        }
        if(++rankedQueue[bracket] == 6)
        {
            Game res = rankedGames[bracket];
            rankedGames[bracket] = new Game(true, gamesDAO, scoresDAO);
            rankedQueue[bracket] = 0;
            return res;
        }
        return rankedGames[bracket];
    }
}
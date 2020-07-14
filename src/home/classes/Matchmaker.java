package home.classes;

import game.classes.Game;

public class Matchmaker {
    private Game game;
    private Game rankedGames[];
    private int inQueue;
    private int rankedQueue[];

    public Matchmaker() {
        game = new Game(false, null);
        inQueue = 0;

        rankedGames = new Game[10];
        rankedQueue = new int[10];
        for(int i = 0; i < 10; i++)
        {
            rankedGames[i] = new Game(true, null);
            rankedQueue[i] = 0;
        }
    }

    synchronized public Game addToQueue() {
        if(game.getRegisteredPlayers() == 0)
        {
            game = new Game(false, null);
            inQueue = 1;
        } else if(++inQueue == 6)
        {
            Game res = game;
            game = new Game(false, null);
            inQueue = 0;
            return res;
        }
        return game;
    }

    synchronized public Game addToRankedQueue(int rating) {
        int bracket = rating / 300;

        if(rankedGames[bracket].getRegisteredPlayers() == 0)
        {
            rankedGames[bracket] = new Game(true, null);
            rankedQueue[bracket] = 1;
        }
        if(++rankedQueue[bracket] == 6)
        {
            Game res = rankedGames[bracket];
            rankedGames[bracket] = new Game(true, null);
            rankedQueue[bracket] = 0;
            return res;
        }
        return rankedGames[bracket];
    }
}
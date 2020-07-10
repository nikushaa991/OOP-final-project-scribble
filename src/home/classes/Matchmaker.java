package home.classes;

import game.Game;

//TODO: i'll do this myself
public class Matchmaker {
    private Game game;
    private int inQueue;
    private static Object lock;

    public Matchmaker() {
        game = new Game(true);
        inQueue = 0;
        lock = new Object();
    }

    public Game addToQueue() throws InterruptedException {
        synchronized (lock)
        {
            lock.wait();
        }
        inQueue++;
        if(inQueue == 6)
        {
            Game res = game;
            game = new Game(true);
            inQueue = 0;
            synchronized (lock)
            {
                lock.notify();
            }
            return res;
        }
        synchronized (lock)
        {
            lock.notify();
        }
        return game;
    }
}
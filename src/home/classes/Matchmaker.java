package home.classes;

import game.Game;

public class Matchmaker {
    private Game game;
    private int inQueue;
    private static Object lock;

    public Matchmaker() {
        game = new Game();
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
            game = new Game();
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
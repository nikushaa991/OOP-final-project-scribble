package home.classes;

public class Matchmaker {
    //private Game game;
    private int inQueue;
    private Object lock;

    public Matchmaker(){
        //game = Game.newInstance();
        inQueue = 0;
        lock = new Object();
    }

    /*
    public Game addToQueue(){
    lock.acquire();
    inQueue++;
    if(inQueue == MAX_PLAYERS)
        {
        Game res = game;
        game = Game.newInstance();
        inQueue = 0;
        lock.release();
        return res;
        }
    lock.release();
    return game;
    }
     */
}
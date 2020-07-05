package game;

import login.User;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

public class Game {
    private ConcurrentHashMap<Integer, HttpSession> sessionMap;
    private int playerCount;
    public static final int N_ROUNDS = 10;
    private Round[] rounds;
    private int curRound;

    public Game(){
        sessionMap = new ConcurrentHashMap<Integer, HttpSession>();
        playerCount = 0;
        rounds = new Round[N_ROUNDS];
        curRound = 0;
    }

    public synchronized void addPlayer(HttpSession session){
        sessionMap.put(playerCount, session);
        Player newPlayer = new Player((User) session.getAttribute("user"), playerCount);
        playerCount++;
    }

    private class Player{
        private User user;
        private int score;
        private int order;
        //socket

        public Player(User user, int order){
            this.order = order;
            score = 0;
            //socket = idk
        }

        public int getScore(){return score;}
        public User getUser(){return user;}
        public int getOrder(){return order;}
        //getSocket
        public void increaseScore(int score){
            this.score += score;
        }
    }

}

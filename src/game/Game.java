package game;

import javax.websocket.Session;
import java.io.IOException;


//SEND TEXT TO PLAYER USING THIS!!!!!!!
//session.getBasicRemote().sendText(text);


public class Game {
    public static final int N_ROUNDS = 18;
    public static final int MAX_PLAYERS = 6;

    private int playerCount;
    private int curRound;
    private String word;
    private Round[] rounds;
    private Player[] Players;

    public Game() {
        Players = new Player[MAX_PLAYERS];
        rounds = new Round[N_ROUNDS];
        playerCount = 0;
        curRound = 0;
        word = "";
    }

    public synchronized void registerSession(Session session) {
        Player newPlayer = new Player(/*session.id*/ 0, playerCount, session);
        Players[playerCount] = newPlayer;
        playerCount++;
        if(playerCount == 2)
            begin();
    }

    private void begin() {
        for(; curRound < N_ROUNDS; curRound++)
        {
            //notifyAll(Players[curRound%playerCount] is artist);
            //sendWordsToArtist();
            //getWordFromArtist();
            //notifyAll(word is word);
            //timer(30sec);
            //recordResults
        }
        chooseWinner();
    }

    //called by player
    private void guessWord(String guess, int order) {
        //if guess == word
        //Players[order].increaseScore();
        //Players[order].notify("Correct guess!");
        //else
        //typeInChat(guess);
    }

    private void chooseWinner() {

    }

    private class Player {
        private int score;
        private int order;
        private String name;
        private Session session;

        public Player(int i, int order, Session session) {
            this.order = order;
            this.session = session;
            //this.name = session.user.getName();
            score = 0;
        }

        public int getScore() {
            return score;
        }

        public String getString() {
            return name;
        }

        public int getOrder() {
            return order;
        }

        public void increaseScore(int score) {
            this.score += score;
        }

        public void notifyPlayer(String text) throws IOException {
            session.getBasicRemote().sendText(text);
        }
    }
}

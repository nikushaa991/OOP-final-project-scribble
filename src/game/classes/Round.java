package game.classes;

import databases.WordsList;
import databases.scores.ScoresDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Round{
    private Player painter;
    private Random rand;
    private String hiddenWord;
    private int guessed;
    public static final int WORD_CHOICE_NUM = 3;
    public static final int PAINTER_CHOICE_TIME = 5;
    public static final int ROUND_DURATION = 35;

    private int gameId;
    private int index;
    private ScoresDAO scoresDAO;
    private Object lock;

    public Round(Player painter, int gameId, ScoresDAO scoresDAO, int index) {
        this.painter = painter;
        rand = new Random();
        this.gameId = gameId;
        this.scoresDAO = scoresDAO;
        this.index = index;
        this.lock = new Object();
    }

    public void OnRoundBegin(Player[] players, boolean[] isActive) throws IOException, InterruptedException {
        // Randomly take word out of WordDB:

        notifyAllPlayers(players, isActive, "M,New Round Started");

        notifyAllPlayers(players, isActive, "N,");
        hiddenWord = "?";
        String Choices[] = new String[WORD_CHOICE_NUM];
        String painterChoice = "";
        int i = 0;
        while (i < WORD_CHOICE_NUM)
        {
            String word = WordsList.wordsList.get(rand.nextInt(WordsList.wordsList.size()));
            for(int j = 0; j < i; j++)
            {
                if(word.equals(Choices[j]))
                {
                    word = Choices[--i];
                    break;
                }
            }
            Choices[i] = word;
            i++;
        }
        for(i = 0; i < WORD_CHOICE_NUM; i++)
            painterChoice += Choices[i] + " ";

        painter.notifyPlayer("A, " + painterChoice);

        new Thread(() -> {
            try
            {
                TimeUnit.SECONDS.sleep(PAINTER_CHOICE_TIME);
                synchronized (lock)
                {
                    lock.notify();
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }).start();
        synchronized (lock)
        {
            try
            {
                lock.wait();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        notifyAllPlayers(players, isActive, "M,Painter has chosen a word");

        if(hiddenWord.equals("?"))
            hiddenWord = Choices[rand.nextInt(Choices.length)];

        painter.notifyPlayer("P,");
        painter.notifyPlayer("M,You have chosen the word: " + hiddenWord);
        painter.setCanGuess(false);
        for(Player p : players)
        {
            if(p != painter && p != null)
                p.setCanGuess(true);
        }
        TimeUnit.SECONDS.sleep(ROUND_DURATION);
    }

    public void OnRoundEnd(Player[] players, boolean[] isActive) throws IOException {
        String result = "S,";
        for(Player p : players)
        {
            if(p != null)
            {
                int score = p.getScore();
                result += p.getName() + "-" + score + " ";
            }
        }
        notifyAllPlayers(players, isActive, result);
    }

    public void OnCorrectGuess(Player[] players, boolean[] isActive, int guesserIndex) throws IOException, SQLException {
        Player guesser = players[guesserIndex];
        notifyAllPlayers(players, isActive, "M," + guesser.getName() + " has guessed the word!");
        int score = CalculateScore(); // TODO: score should be based on order, not time, needs implementing anyway.
        guesser.increaseScore(score);
        scoresDAO.newScore(guesser.getName(), gameId, index, score); //write to db
        guesser.setCanGuess(false);
        guessed++;
    }

    public void OnIncorrectGuess(Player[] players, boolean[] isActive, int guesserIndex, String guess) throws IOException {
        notifyAllPlayers(players, isActive, "C," + players[guesserIndex].getName() + ": " + guess);
    }

    public void notifyAllPlayers(Player[] players, boolean[] isActive, String text) throws IOException {
        for(int i = 0; i < Game.MAX_PLAYERS; i++)
            if(isActive[i])
                players[i].notifyPlayer(text);
    }

    public boolean CheckGuess(String guess) {
        if(guess.toLowerCase().equals(hiddenWord.toLowerCase()))
            return true;
        return false;
    }

    private int CalculateScore() {
        return (Game.MAX_PLAYERS - guessed) * 10;
    }

    public void ChooseHiddenWord(String str) {
        hiddenWord = str;
        synchronized (lock)
        {
            try
            {
                lock.notify();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

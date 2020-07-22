package game.classes;

import databases.WordsList;
import databases.scores.ScoresDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Round {
    private static final int WORD_CHOICE_NUM = 3;
    private static final int PAINTER_CHOICE_TIME = 10;
    private static final int ROUND_DURATION = 60;
    private static final String defaultWord = "NOWORD";
    private final Player painter;
    private final Random rand;
    private final int gameId;
    private final int index;
    private final ScoresDAO scoresDAO;
    private final Object lock;
    private final Game game;
    private String hiddenWord;
    private int guessed;

    public Round(Player painter, Game game, int gameId, ScoresDAO scoresDAO, int index) {
        this.painter = painter;
        this.gameId = gameId;
        this.game = game;
        this.scoresDAO = scoresDAO;
        this.index = index;
        this.lock = new Object();
        rand = new Random();

    }

    String[] GenerateWordsForPainter() {
        String[] Choices = new String[WORD_CHOICE_NUM];

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

        return Choices;
    }

    void UpdateScores(Player[] players, boolean[] isActive) {
	    String result = "S,";
	    for(Player p : players)
	    {
	        if(p != null)
	        {
	            int score = p.getScore();
	            result += p.getName() + " - " + score + ",";
	        }
	    }
	    notifyAllPlayers(players, isActive, result);
    }

    public void OnRoundBegin(Player[] players, boolean[] isActive) throws InterruptedException, IOException {
    	UpdateScores(players, isActive);

        notifyAllPlayers(players, isActive, "N,");

        for(Player p : players)
            if(p != null)
                p.setCanGuess(true);

        hiddenWord = defaultWord;

        StringBuilder painterChoice = new StringBuilder();

        String[] Choices = GenerateWordsForPainter();

        for(int i = 0; i < WORD_CHOICE_NUM; i++)
            painterChoice.append(Choices[i]).append(" ");

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

        if(hiddenWord.equals(defaultWord))
            hiddenWord = Choices[rand.nextInt(Choices.length)];

        painter.notifyPlayer("P,");
        painter.notifyPlayer("M,You have chosen the word: " + hiddenWord);
        painter.setCanGuess(false);

        for(int i = 0; i < 30 ; i++)
            if(guessed != game.getActivePlayerCount() - 1)
                TimeUnit.SECONDS.sleep(ROUND_DURATION / 30);
            else break;

        notifyAllPlayers(players, isActive, "M,The word was: " + hiddenWord);
    }

    public void OnRoundEnd(Player[] players, boolean[] isActive) throws IOException {
    	UpdateScores(players, isActive);
    }

    public void OnCorrectGuess(Player[] players, boolean[] isActive, int guesserIndex) throws SQLException {
        Player guesser = players[guesserIndex];
        notifyAllPlayers(players, isActive, "M," + guesser.getName() + " has guessed the word!");
        int score = CalculateScore();
        guesser.increaseScore(score);
        scoresDAO.newScore(guesser.getName(), gameId, index, score); //write to db
        guesser.setCanGuess(false);
        guessed++;
    }

    public void OnIncorrectGuess(Player[] players, boolean[] isActive, int guesserIndex, String guess) {
        notifyAllPlayers(players, isActive, "C," + Game.colors[guesserIndex] + ',' + players[guesserIndex].getName() + "," + guess);
    }

    public void notifyAllPlayers(Player[] players, boolean[] isActive, String text) {
        for(int i = 0; i < Game.MAX_PLAYERS; i++)
            if(isActive[i])
                players[i].notifyPlayer(text);
    }

    public boolean CheckGuess(String guess) {
        return guess.toLowerCase().equals(hiddenWord.toLowerCase()) && !guess.equals(defaultWord);
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

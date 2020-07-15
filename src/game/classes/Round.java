package game.classes;

import databases.WordsList;

import java.io.IOException;
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

    public Round(Player painter)
    {
        this.painter = painter;
        rand = new Random();
    }

    public void OnRoundBegin(Player[] players, boolean[] isActive) throws IOException, InterruptedException {
        // Randomly take word out of WordDB:

        hiddenWord = "?";
        String Choices[] = new String[WORD_CHOICE_NUM];
        String painterChoice = "";
        int i = 0;
        while(i < WORD_CHOICE_NUM) {
            String word = WordsList.wordsList.get(rand.nextInt(WordsList.wordsList.size()));
            for (int j = 0; j < i; j++)
            {
                if (word.equals(Choices[j]))
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

        notifyAllPlayers(players, isActive,"M, Painter is choosing word");
        TimeUnit.SECONDS.sleep(PAINTER_CHOICE_TIME);
        notifyAllPlayers(players, isActive,"M, Painter has chosen word");

        if(hiddenWord.equals("?"))
            hiddenWord = Choices[rand.nextInt(Choices.length)];

		notifyAllPlayers(players, isActive,"M, New Round Started");

        notifyAllPlayers(players, isActive,"N,");

        painter.notifyPlayer("P,");
        painter.notifyPlayer("M, The word is: " + hiddenWord);
        painter.setCanGuess(false);
        for(Player p : players)
        {
            if(p != painter && p!= null)
                p.setCanGuess(true);
        }
        TimeUnit.SECONDS.sleep(ROUND_DURATION);
    }

    public void OnRoundEnd(Player[] players, boolean[] isActive) throws IOException
    {
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

        notifyAllPlayers(players, isActive, "M, Round Ended");
    }

    public void OnCorrectGuess(Player[] players, boolean[] isActive, int guesserIndex) throws IOException
    {
        Player guesser = players[guesserIndex];
        notifyAllPlayers(players, isActive, "M," + guesser.getName() + " has guessed the word!");
        int score = CalculateScore(); // TODO: score should be based on order, not time, needs implementing anyway.
        guesser.increaseScore(score);
        guesser.setCanGuess(false);
        guessed++;
    }

    public void OnIncorrectGuess(Player[] players, boolean[] isActive, int guesserIndex, String guess) throws IOException //TODO: do we really need this method?
    {
        notifyAllPlayers(players, isActive, "C," + players[guesserIndex].getName() + ": " + guess);
    }

    public void notifyAllPlayers(Player[] players, boolean[] isActive, String text) throws IOException { //TODO: move this to a new negotiator class instead.
        for(int i = 0; i < Game.MAX_PLAYERS; i++) //TODO: FIX THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!! replace current code with commented code
            if(isActive[i])
                players[i].notifyPlayer(text);
    }

    public boolean CheckGuess(String guess)
    {
        if(guess.toLowerCase().equals(hiddenWord.toLowerCase()))
            return true;
        return false;
    }

    private int CalculateScore()
    {
        return (Game.MAX_PLAYERS - guessed) * 10;
    }

    public void ChooseHiddenWord(String str)
    {
        hiddenWord = str;
    }

}

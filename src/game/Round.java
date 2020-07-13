package game;

import login.User;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/*
    M, - text gets shown in chat
    S, - score gets shown in chat
    A, - Array of WordChoices
 */

public class Round{
    private Player painter;
    private Random rand;
    private String hiddenWord;
    public static final int WORD_CHOICE_NUM = 3;
    public static final int DURATION = 2000;

    public Round(Player painter)
    {
        this.painter = painter;
        rand = new Random(); //TODO: new random?? instanceof maybe
    }

    public void OnRoundBegin(Player[] players) throws IOException, InterruptedException {
        // Randomly take word out of WordDB:

        String Choices[] = new String[WORD_CHOICE_NUM];
        String painterChoice = "";
        for(int i = 0; i < Choices.length; i++)
        {
            Choices[i] = WordsList.wordsList.get(rand.nextInt(Choices.length));
            painterChoice += Choices[i] + " ";
        }


        painter.notifyPlayer("A, " + painterChoice);



        hiddenWord = WordsList.wordsList.get(rand.nextInt(3)); //TODO: choose a list of unique words instead

		notifyAllPlayers(players, "M, New Round Started");

        notifyAllPlayers(players, "N,");
        painter.notifyPlayer("P,");
        painter.notifyPlayer("M, The word is: " + hiddenWord); //TODO: send him a list of words instead, wait for his answer
        //TODO: might not be needed, we can add this to JS instead.
        painter.setCanGuess(false);
        for(Player p : players)
        {
            if(p != painter && p!= null)
                p.setCanGuess(true);
        }
        TimeUnit.SECONDS.sleep(20);
        // Painter chooses one word
        // HiddenWord = that word;
    }

    public void OnRoundEnd(Player[] players) throws IOException
    {
        String result;
        result = "S,";

        for(Player p : players)
        {
            if(p != null)
            {
                int score = p.getScore();
                result += p.getName() + "-" + score + " ";
            }
        }
        notifyAllPlayers(players, result);

        notifyAllPlayers(players, "M, Round Ended");


        // foreach(Player)
            // int score = Player.getScore();
            // Update Session user score
    }

    // Is used when a given player writes a correct guess
    public void OnCorrectGuess(Player[] players, int guesserIndex) throws IOException
    {
        Player guesser = players[guesserIndex];
        String finalString = "M, !! " + guesser.getName() + " has guessed the word!!"; //TODO: write directly into notifyAllPlayers maybe?
        notifyAllPlayers(players, finalString);
        int score = CalculateScore(hiddenWord, 10); // TODO: score should be based on order, not time, needs implementing anyway.
        guesser.increaseScore(score);
        guesser.setCanGuess(false);
        // Log out "guesser.name has guessed the word"
        // int score = Calculate score based on word difficulty and time
        // guesser.IncreaseScore(score);
        // Disable guesser-s ability to guess again
    }

    public void OnIncorrectGuess(Player[] players, int guesserIndex, String guess) throws IOException //TODO: do we really need this method?
    {
        notifyAllPlayers(players, "C," + players[guesserIndex].getName() + ": " + guess);
    }

    public void OnCloseGuess(Player guesser) throws IOException
    {
        guesser.notifyPlayer("M, You're close to solution");
        // Log out "guesser.name is close to the solution"
    }

    public void notifyAllPlayers(Player[] players, String text) throws IOException { //TODO: move this to a new negotiator class instead.
        for(Player p : players)
            if(p != null)
                p.notifyPlayer(text);
    }


    /*
    * FUNCTION: CheckGuess
    * checks if the parameter "guess" is HiddenWord
    * Returnes:
    *   1 --- If guess == HiddenWord
    *   2 --- If Guess ~ HiddenWord
    *   0 --- If Guess != HiddenWord
    * */
    public int CheckGuess(String guess) //TODO: implement close guess
    {
        if(guess.equals(hiddenWord))
            return 1;
        return 0;
    }

    private int CalculateScore(String word, int time) //TODO: make it based on order instead of time, implement.
    {
        return 10;
    }

}

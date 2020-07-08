package game;

import login.User;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Random;

public class Round extends Thread {
    private Session painterSession;
    private Random rand;
    private String hiddenWord;
    public static final int DURATION = 2000;

    public Round(Session painterSession)
    {
        this.painterSession = painterSession;
        rand = new Random();
    }

    public void OnRoundBegin() throws IOException, InterruptedException {
        // Randomly take word out of WordDB:
        hiddenWord = WordsList.wordsList.get(rand.nextInt(3));
        painterSession.getBasicRemote().sendText("WORD," + hiddenWord);
        Thread.sleep(DURATION);
        // Painter chooses one word
        // HiddenWord = that word;
    }

    public void OnRoundEnd()
    {
        // foreach(Player)
            // int score = Player.getScore();
            // Update Session user score
    }

    // Is used when a given player writes a correct guess
    public void OnCorrectGuess(Player guesser)
    {
        // Log out "guesser.name has guessed the word"
        // int score = Calculate score based on word difficulty and time
        // guesser.IncreaseScore(score);
        // Disable guesser-s ability to guess again
    }

    public void OnCloseGuess(Player guesser)
    {
        // Log out "guesser.name is close to the solution"
    }



    /*
    * FUNCTION: CheckGuess
    * checks if the parameter "guess" is HiddenWord
    * Returnes:
    *   1 --- If guess == HiddenWord
    *   2 --- If Guess ~ HiddenWord
    *   0 --- If Guess != HiddenWord
    * */
    public int CheckGuess(String guess)
    {
        return 0;
    }

    private int CalculateScore(String word, int time)
    {
        return 0;
    }

}

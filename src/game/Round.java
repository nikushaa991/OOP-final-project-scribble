package game;

import login.User;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Random;

public class Round{
    private Session painterSession;
    private Player painter;
    private Random rand;
    private String hiddenWord;
    public static final int DURATION = 2000;

    public Round(Player painter)
    {
        this.painter = painter;
        this.painterSession = painter.getSession();
        rand = new Random();
    }

    public void OnRoundBegin(Player[] players) throws IOException, InterruptedException {
        // Randomly take word out of WordDB:
        hiddenWord = WordsList.wordsList.get(rand.nextInt(3));
        painterSession.getBasicRemote().sendText("WORD," + hiddenWord);
        painter.SetCanGuess(false);
        for(Player p : players)
        {
            if(p != painter)
                p.SetCanGuess(true);
        }
        wait(DURATION);
        // Painter chooses one word
        // HiddenWord = that word;
    }

    public void OnRoundEnd(Player[] players) throws IOException {
        for(Player p : players)
        {
            if(p != null)
            {
                int score = p.GetScore();
                Session playerSession = p.getSession();
                String strScore = p.GetName() + " " + score;
                playerSession.getBasicRemote().sendText(strScore);
            }
        }

        // foreach(Player)
            // int score = Player.getScore();
            // Update Session user score
    }

    // Is used when a given player writes a correct guess
    public void OnCorrectGuess(Player guesser) throws IOException
    {
        String finalString = "!! " + guesser.GetName() + " has guessed the word!!";
        guesser.notifyPlayer(finalString);
        int score = CalculateScore(hiddenWord, 10); // TODO: time should be changed
        guesser.IncreaseScore(score);
        guesser.SetCanGuess(false);
        // Log out "guesser.name has guessed the word"
        // int score = Calculate score based on word difficulty and time
        // guesser.IncreaseScore(score);
        // Disable guesser-s ability to guess again
    }

    public void OnIncorrectGuess(Player guesser, String guess) throws IOException
    {
        guesser.notifyPlayer(guesser.GetName() + ": " + guess);
        // Log out "guesser.name: " + "guess";
    }

    public void OnCloseGuess(Player guesser) throws IOException
    {
        guesser.notifyPlayer(guesser.GetName() + " is close to the solution");
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
        if(guess.equals(hiddenWord))
            return 1;
        return 0;
    }

    private int CalculateScore(String word, int time)
    {
        return 10;
    }

}

package game;

import login.User;

public class Round {
    private User painter;

    private String HiddenWord;

    public Round(User painter)
    {
        this.painter = painter;
    }

    public void OnRoundBegin()
    {
        // Randomly take 3 words out of WordDB
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

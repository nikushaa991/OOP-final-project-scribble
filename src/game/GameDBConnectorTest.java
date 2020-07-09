package game;

<<<<<<< HEAD
//import javafx.util.Pair;
=======

import main.java.Pair;
>>>>>>> ad2e4a4170a7fff1912839f082cee48c841db68f
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameDBConnectorTest {

    /* Tests basic functionality of the games table*/
    @Test
    public void test1() throws SQLException {
        GameDBConnector games = new GameDBConnector();
<<<<<<< HEAD
        games.newGame(true,-1,100);
        games.newGame(true,-2,200);
        games.newGame(true,-3,34);
        games.newGame(true,-4,1000);
        games.newGame(true,-5,0);
//        ArrayList<Pair<Integer, Integer>> topScores = games.topScores(3);
//        assertEquals(topScores.get(0).getValue(), 1000);
//        assertEquals(topScores.get(1).getValue(), 200);
//        assertEquals(topScores.get(2).getValue(), 100);
        games.deleteGame(-1);
        games.deleteGame(-2);
        games.deleteGame(-3);
        games.deleteGame(-4);
        games.deleteGame(-5);
=======
        try {
            games.newGame(true, -1, 100);
            games.newGame(true, -2, 200);
            games.newGame(true, -3, 34);
            games.newGame(true, -4, 1000);
            games.newGame(true, -5, 0);
            ArrayList<Pair<Integer, Integer>> topScores = games.topScores(3);
            assertEquals(topScores.get(0).getSecond(), 1000);
            assertEquals(topScores.get(1).getSecond(), 200);
            assertEquals(topScores.get(2).getSecond(), 100);
        } finally {
            games.deleteGame(-1);
            games.deleteGame(-2);
            games.deleteGame(-3);
            games.deleteGame(-4);
            games.deleteGame(-5);
        }
>>>>>>> ad2e4a4170a7fff1912839f082cee48c841db68f
    }

}
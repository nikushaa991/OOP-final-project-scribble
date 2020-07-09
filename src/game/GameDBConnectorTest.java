package game;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameDBConnectorTest {

    /* Tests basic functionality of the games table*/
    @Test
    public void test1() throws SQLException {
        GameDBConnector games = new GameDBConnector();
        games.newGame(true,-1,100);
        games.newGame(true,-2,200);
        games.newGame(true,-3,34);
        games.newGame(true,-4,1000);
        games.newGame(true,-5,0);
        ArrayList<Pair<Integer, Integer>> topScores = games.topScores(3);
        assertEquals(topScores.get(0).getValue(), 1000);
        assertEquals(topScores.get(1).getValue(), 200);
        assertEquals(topScores.get(2).getValue(), 100);
        games.deleteGame(-1);
        games.deleteGame(-2);
        games.deleteGame(-3);
        games.deleteGame(-4);
        games.deleteGame(-5);
    }

}
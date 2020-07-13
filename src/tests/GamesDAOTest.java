package tests;


import databases.games.GamesDAO;
import utils.Pair;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GamesDAOTest {

    /* Tests basic functionality of the games table*/
    @Test
    public void test1() throws SQLException {
        GamesDAO games = new GamesDAO();
        try {
            games.newGame(true, "-1", 100);
            games.newGame(true, "-2", 200);
            games.newGame(true, "-3", 34);
            games.newGame(true, "-4", 1000);
            games.newGame(true, "-5", 0);
            ArrayList<Pair<String, Integer>> topScores = games.topScores(3);
            assertEquals(topScores.get(0).getSecond(), 1000);
            assertEquals(topScores.get(1).getSecond(), 200);
            assertEquals(topScores.get(2).getSecond(), 100);
        } finally {
            games.deleteGame("-1");
            games.deleteGame("-2");
            games.deleteGame("-3");
            games.deleteGame("-4");
            games.deleteGame("-5");
        }
    }

}
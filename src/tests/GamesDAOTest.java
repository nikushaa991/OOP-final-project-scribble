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
            int game1 = games.newGame(true);
            int game2 = games.newGame(true);
            games.updateGame(game1, "bubuTest", 2000000001);
            games.updateGame(game2, "ripTest", 2000000000);
            ArrayList<Pair<String, Integer>> topScores = games.topScores(2);
            assertEquals(topScores.get(0).getSecond(), 2000000001);
            assertEquals(topScores.get(1).getSecond(), 2000000000);
        } finally {
            games.deleteGame("bubuTest");
            games.deleteGame("ripTest");
        }
    }

}
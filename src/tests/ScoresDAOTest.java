package tests;

import databases.scores.ScoresDAO;
import utils.Pair;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ScoresDAOTest {

    /* Tests basic functionality of scores table*/
    @Test
    public void test1() throws SQLException {
        ScoresDAO scoresDao = new ScoresDAO();
        try {
            scoresDao.newScore("-1", -1, -1, 1000000000);
            scoresDao.newScore("-2", -1, -1, 200000000);
            scoresDao.newScore("-3", -1, -2, 30);
            scoresDao.newScore("-1", -2, -2, 1000000000);
            scoresDao.newScore("-2", -3, -1, 50000000);
            ArrayList<Pair<String, Integer>> topScores = scoresDao.overallTopScores(2);
            assertEquals(topScores.get(0).getSecond(), 2000000000);
            assertEquals(topScores.get(1).getFirst(), "-2");
            assertEquals(scoresDao.userScore("-1"), 2000000000);
        } finally {
            scoresDao.deleteScores("-1");
            scoresDao.deleteScores("-2");
            scoresDao.deleteScores("-3");
        }
    }
}
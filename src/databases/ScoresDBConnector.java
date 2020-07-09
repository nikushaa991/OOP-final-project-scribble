package databases;

import main.java.DBConnector;
import main.java.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ScoresDBConnector extends DBConnector {

    public ScoresDBConnector() throws SQLException {
        super();
        tableName = "scores";
    }

    /* Add new score for user in specific game/round */
    public void newScore(int userId, int gameId, int roundId, int score) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tableName +
                " VALUES (?, ?, ?, ?)");
        ps.setInt(1, userId);
        ps.setInt(2, gameId);
        ps.setInt(3, roundId);
        ps.setInt(4, score);
        ps.execute();
    }

    /* delete scores for specific user. mainly for testing purposes */
    public void deleteScores(int userId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName + " WHERE USER_ID = ?");
        ps.setInt(1, userId);
        ps.execute();
    }

    /* returns list of all-time top scorer user ids and respective total scores */
    public  ArrayList<Pair<Integer, Integer>> overallTopScores(int count) throws SQLException {
        ArrayList<Pair<Integer, Integer> > top = new ArrayList<>();
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery(
                "select * from (SELECT USER_ID, SUM(SCORE) SUM_SCORE FROM " + tableName +
                " GROUP BY USER_ID) a ORDER BY SUM_SCORE DESC LIMIT " + count + ";");
        while (rs.next()) {
            Pair<Integer, Integer> nextUser = new Pair(rs.getInt(1), rs.getInt(2));
            top.add(nextUser);
        }
        return top;
    }
}

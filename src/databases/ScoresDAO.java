package databases;

import utils.DBConnector;
import utils.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ScoresDAO extends DBConnector {

    public ScoresDAO() throws SQLException {
        super();
        tableName = "scores";
    }

    /* Add new score for user in specific game/round */
    public void newScore(String username, int gameId, int roundId, int score) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tableName +
                " VALUES (?, ?, ?, ?)");
        ps.setString(1, username);
        ps.setInt(2, gameId);
        ps.setInt(3, roundId);
        ps.setInt(4, score);
        ps.execute();
    }

    /* delete scores for specific user. mainly for testing purposes */
    public void deleteScores(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName + " WHERE USERNAME = ?");
        ps.setString(1, username);
        ps.execute();
    }

    /* returns list of all-time top scorer usernames and respective total scores */
    public  ArrayList<Pair<String, Integer>> overallTopScores(int count) throws SQLException {
        ArrayList<Pair<String, Integer> > top = new ArrayList<>();
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery(
                "select * from (SELECT USERNAME, SUM(SCORE) SUM_SCORE FROM " + tableName +
                " GROUP BY USERNAME) a ORDER BY SUM_SCORE DESC LIMIT " + count + ";");
        while (rs.next()) {
            Pair<String, Integer> nextUser = new Pair(rs.getString(1), rs.getInt(2));
            top.add(nextUser);
        }
        return top;
    }
}

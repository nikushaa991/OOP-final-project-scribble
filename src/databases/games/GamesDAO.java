package databases.games;


import utils.DBConnector;
import utils.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GamesDAO extends DBConnector {
    private int gamesCount;

    public GamesDAO() throws SQLException {
        super();
        tableName = "games";
        nrows = nrow();
    }

    /* Adds new game to table */
    public  void newGame(boolean isRanked, String winnerUsername, int winnerScore) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tableName +
                " VALUES (?, DATE(SYSDATE()), ?, ?, ?)");
        nrowLock.lock();
        ps.setInt(1, nrows);
        nrows++;
        nrowLock.unlock();
        ps.setString(2, winnerUsername);
        ps.setInt(3, winnerScore);
        ps.setBoolean(4, isRanked);
        ps.execute();
    }

    /* Delete game from table identified by game id. Mainly created for testing purposes */
    public void deleteGame(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName + " WHERE WINNER_USERNAME = ?");
        ps.setString(1, username);
        ps.execute();
    }

    /* gets top scores of single game and scorers. number of top records is passed as an argument by client.
    * First integer of the pair is username and second integer is score of the user.
    * */
    public ArrayList<Pair<String, Integer>> topScores(int count) throws SQLException {
        ArrayList<Pair<String, Integer> > topScores = new ArrayList<>();
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + tableName +
                " WHERE RANKED = true ORDER BY WINNING_SCORE DESC LIMIT " + count + ";");
        while (rs.next()) {
            Pair<String, Integer> nextUser = new Pair(rs.getString(3), rs.getInt(4));
            topScores.add(nextUser);
        }
        return topScores;
    }

}

package game;


import login.Encryptor;
import login.User;
import main.java.DBConnector;
import main.java.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GameDBConnector extends DBConnector {
    private int gamesCount;

    public GameDBConnector() throws SQLException {
        super();
        tableName = "games";
        nrows = nrow();
    }

    /* Adds new game to table */
    public void newGame(boolean isRanked, int winnerId, int winnerScore) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tableName +
                " VALUES (?, DATE(SYSDATE()), ?, ?, ?)");
        nrowLock.lock();
        ps.setInt(1, nrows);
        nrows++;
        nrowLock.unlock();
        ps.setInt(2, winnerId);
        ps.setInt(3, winnerScore);
        ps.setBoolean(4, isRanked);
        ps.execute();
    }

    /* Delete game from table identified by game id. created for testing purposes */
    public void deleteGame(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
        ps.setInt(1, id);
        ps.execute();
    }

    /* gets top scores of single game and scorers. number of top records is passed as an argument by client.
    * First integer of the pair is ID and second integer is score of the user.
    * */
    public ArrayList<Pair<Integer, Integer>> topScores(int count) throws SQLException {
        ArrayList<Pair<Integer, Integer> > topScores = new ArrayList<>();
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + tableName +
                " WHERE RANKED = true ORDER BY WINNING_SCORE DESC LIMIT " + count + ";");
        while (rs.next()) {
            Pair<Integer, Integer> nextUser = new Pair(rs.getInt(3), rs.getInt(4));
            topScores.add(nextUser);
        }
        return topScores;
    }

}

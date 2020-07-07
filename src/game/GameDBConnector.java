package game;

import login.Encryptor;
import login.User;
import main.java.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDBConnector extends DBConnector {
    private int gamesCount;

    public GameDBConnector() throws SQLException {
        super();
        tableName = "games";
        nrows = nrow();
    }

    /* Adds new game to table */
    public void newGame(boolean isRanked, int winnerId, int winnerScore) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?, SYSDATE(), ?, ?, ?)");
        nrowLock.lock();
        ps.setInt(1, nrows);
        nrows++;
        nrowLock.unlock();
        ps.setInt(3, winnerId);
        ps.setInt(4, winnerScore);
        ps.setBoolean(5, isRanked);
        ps.execute();
    }

    /* gets top scores of single game and scorers. number of top records is passed as an argument by client. */


}

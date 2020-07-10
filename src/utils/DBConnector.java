//TODO: dislike the main.java package name, maybe utils or libs or something?
package utils;

import databases.SingletonDb;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;

public abstract class DBConnector {
    protected static Connection connection;
    protected String tableName;
    protected int nrows;
    protected ReentrantLock nrowLock;

    public DBConnector(){
        connection = SingletonDb.getInstance();
        nrowLock = new ReentrantLock();
    }

    /*
     * Closes connection to the database
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /* Returns number of records in the table */
    public int nrow() throws SQLException {
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT count(*) FROM " + tableName +";");
        rs.next();
        return rs.getInt(1);
    }
}

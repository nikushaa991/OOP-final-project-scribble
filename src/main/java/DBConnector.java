//TODO: dislike the main.java package name, maybe utils or libs or something?
package main.java;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;

public abstract class DBConnector {
    protected Connection connection;
    protected String tableName;
    protected int nrows;
    protected ReentrantLock nrowLock;

    public DBConnector(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    DatabaseCredentials.url,
                    DatabaseCredentials.user,
                    DatabaseCredentials.password);
            Statement useDbStm = connection.createStatement();
            useDbStm.executeQuery("USE SCRIBBLE;");
            nrowLock = new ReentrantLock();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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

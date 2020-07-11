package utils;

import databases.SingletonDb;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;

/* DB connector is superclass of all DAOs.
*  Calling its constructor many times initializes connection to DB once,
*  because connection itself is a singleton class. This class contains fields shared
*  by it's subclasses: connection, table names, number of rows and a lock for
*  increasing number of rows synchronously.
*
*  NOTE: Factory design pattern was considered for DAOs, but each method of DAOs
*  takes different number/type of arguments, thus making Factory less intuitive.
* */
public abstract class DBConnector {
    protected static Connection connection;
    protected String tableName;
    protected int nrows;
    protected ReentrantLock nrowLock;

    /* Constructor sets up connection it it is called for the first time,
    * initializes reentrant lock for synchronous behavior handling.
    * */
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

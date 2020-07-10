package login;

import utils.DBConnector;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class UsersDAO extends DBConnector {

    /*
     * creates and connects to a database
     */
    public UsersDAO() throws SQLException {
        super();
        tableName = "users";
        nrows = nrow();
    }

    /* converts table records to a list and writes them on passed argument */
    public ArrayList<User> toList() throws SQLException {
        ArrayList<User> usersArray = new ArrayList<User>();
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + tableName + ";");
        while (rs.next()) {
            User newItem = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            usersArray.add(newItem);
        }
        return usersArray;
    }

    /* returns user record from knowing it's username */
    public User getUser(String username) throws SQLException {
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + tableName + " WHERE username = \"" + username + "\"");
        if(rs.next())
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
        return null;
    }

    /* checks if user exists in the table */
    public boolean exists(String username) throws SQLException {
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + tableName + " WHERE username = \"" + username + "\"");
        if(rs.next())
            return true;
        return false;
    }

    /* Creates and adds new user to the table if such does not exist yet */
    public void newUser(String username, String password) throws SQLException, NoSuchAlgorithmException {
        if(!exists(username)) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?, ?, ?)");
            nrowLock.lock();
            ps.setInt(1, nrows);
            nrows++;
            nrowLock.unlock();
            ps.setString(2, username);
            ps.setString(3, Encryptor.shaVal(password));
            ps.execute();
        }
    }

    /* Deletes user from table. This will be needed to testing purposes */
    public void deleteUser(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName + " WHERE username = ?");
        ps.setString(1, username);
        ps.execute();
    }

    /* Checks if SHA value of password matches username's password */
    public boolean passwordMatches(String username, String password) throws NoSuchAlgorithmException, SQLException {
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + tableName +" WHERE username = \"" + username + "\"");
        if(rs.next()){
            if(rs.getString(3).equals(Encryptor.shaVal(password)))
                return true;
        }
        return false;
    }
}
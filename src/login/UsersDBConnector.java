package login;

import login.Encryptor;
import login.User;
import main.java.DBConnector;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersDBConnector extends DBConnector {

    private final String usersTableName = "users";
    private int usersCount;

    /*
     * creates and connects to a database
     */
    public UsersDBConnector() throws SQLException {
        super();
        usersCount = usersCount();
    }

    /* converts table records to a list */
    public ArrayList<User> toList() throws SQLException {
        ArrayList<User> result = new ArrayList<User>();
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + usersTableName + ";");
        while (rs.next()) {
            User newItem = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            result.add(newItem);
        }
        return result;
    }

    /* converts table records to hashMap */
    public HashMap<String, String> toMap() throws SQLException {
        HashMap<String, String> result = new HashMap<String, String>();
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + usersTableName + ";");
        while (rs.next()) {
            String username = rs.getString(2);
            String password =  rs.getString(3);
            result.put(username, password);
        }
        return result;
    }

    /* returns user record from knowing it's username */
    public User getUser(String username) throws SQLException {
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + usersTableName + " WHERE username = \"" + username + "\"");
        if(rs.next())
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
        return null;
    }

    /* checks if user exists in the table */
    public boolean exists(String username) throws SQLException {
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + usersTableName + " WHERE username = \"" + username + "\"");
        if(rs.next())
            return true;
        return false;
    }

    /* Returns number of users in the table */
    public int usersCount() throws SQLException {
        int result = 0;
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + usersTableName +";");
        if(rs.next())
            result++;
        return result;
    }

    /* Creates and adds new user to the table if such does not exist yet */
    public void newUser(String username, String password) throws SQLException, NoSuchAlgorithmException {
        if(!exists(username)) {
            User newUser = new User(usersCount, username, password);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " + usersTableName + " VALUES (?, ?, ?)");
            ps.setInt(1, usersCount);
            ps.setString(2, username);
            ps.setString(3, Encryptor.shaVal(password));
            ps.execute();
        }
    }

    /* Checks if SHA value of password matches username's password */
    public boolean passwordMatches(String username, String password) throws NoSuchAlgorithmException, SQLException {
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery("SELECT * FROM " + usersTableName +" WHERE username = " + username + ";");
        if(rs.next()){
            if(rs.getString(3).equals(Encryptor.shaVal(password)))
                return true;
        }
        return false;
    }
}
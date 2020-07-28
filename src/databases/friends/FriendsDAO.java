package databases.friends;

import utils.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 *
 * */
public class FriendsDAO extends DBConnector {

    public FriendsDAO() {
        super();
        tableName = "friends";
    }

    /* adds new friendship to table after checking that such friendship does not exit yet*/
    public void newFriendship(String user1, String user2) throws SQLException {
        Statement queryStm = connection.createStatement();
        PreparedStatement stmt = connection.prepareStatement("select USERNAME_1 FROM " +
                tableName + " WHERE USERNAME_1 = ? AND USERNAME_2 = ?;");
        stmt.setString(1, user1);
        stmt.setString(2, user2);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next())
        {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tableName +
                    " VALUES (?, ?), (?, ?)");
            ps.setString(1, user1);
            ps.setString(2, user2);
            ps.setString(3, user2);
            ps.setString(4, user1);
            ps.execute();
        }
    }

    /* removes friendship from table */
    public void deleteFriendship(String user1, String user2) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName +
                " WHERE (USERNAME_1 = ? AND USERNAME_2 = ?) OR (USERNAME_2 = ? AND USERNAME_1 = ?) ");
        ps.setString(1, user1);
        ps.setString(2, user2);
        ps.setString(3, user1);
        ps.setString(4, user2);
        ps.execute();
    }

    /* Returns ArrayList representation of friends list */
    public ArrayList<String> friendsList(String user) throws SQLException {
        ArrayList<String> friends = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("select USERNAME_2 FROM " +
                tableName + " WHERE USERNAME_1 = ?;");
        stmt.setString(1, user);
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            String friend = rs.getString(1);
            friends.add(friend);
        }
        return friends;
    }
}

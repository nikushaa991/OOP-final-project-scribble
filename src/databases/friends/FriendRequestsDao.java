package databases.friends;

import utils.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/* Todo: This class is very similar TO FriendsDao. I will do something about it (interitance or other) */
public class FriendRequestsDao extends DBConnector implements Friends{

    public FriendRequestsDao() {
        super();
        tableName = "friendRequests";
    }

    /* adds new friendship request to table, after checking that such friend request does not exist yet */
    public void add(String to, String from) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("select USERNAME_FROM FROM " + tableName +
                " WHERE USERNAME_TO = ? AND USERNAME_FROM = ?;");
        stmt.setString(1, to);
        stmt.setString(2, from);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next())
        {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tableName +
                    " VALUES (?, ?)");
            ps.setString(1, to);
            ps.setString(2, from);
            ps.execute();
        }
    }

    /* removes friendship request from table */
    public void delete(String to, String from) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName +
                " WHERE (USERNAME_TO = ? AND USERNAME_FROM = ?);");
        ps.setString(1, to);
        ps.setString(2, from);
        ps.execute();
    }

    /* Returns ArrayList representation of friends requests list */
    public ArrayList<String> toList(String to) throws SQLException {
        ArrayList<String> friendRequests = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("select USERNAME_FROM FROM " +
                tableName + " WHERE USERNAME_TO = ?;");
        stmt.setString(1, to);
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            String friend = rs.getString(1);
            friendRequests.add(friend);
        }
        return friendRequests;
    }
}

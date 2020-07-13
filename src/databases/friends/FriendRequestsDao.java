package databases.friends;

import utils.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/* Todo: This class is very similar TO FriendsDao. I will do something about it (interitance or other) */
public class FriendRequestsDao extends DBConnector {

    public FriendRequestsDao(){
        super();
        tableName = "friendRequests";
    }

    /* adds new friendship request to table, after checking that such friend request does not exist yet */
    public void newFriendshipRequest(String to, String from) throws SQLException {
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery(
                "select USERNAME_FROM FROM " + tableName + " WHERE USERNAME_TO = \"" + to + "\" AND USERNAME_FROM = \"" + from + "\";");
        if(!rs.next()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tableName +
                    " VALUES (?, ?)");
            ps.setString(1, to);
            ps.setString(2, from);
            ps.execute();
        }
    }

    /* removes friendship request from table */
    public void deleteFriendshipRequest(String to, String from) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName +
                " WHERE (USERNAME_TO = ? AND USERNAME_FROM = ?);");
        ps.setString(1, to);
        ps.setString(2, from);
        ps.execute();
    }

    /* Returns ArrayList representation of friends requests list */
    public ArrayList<String> friendshipRequestsList(String to) throws SQLException {
        ArrayList<String> friendRequests = new ArrayList<>();
        Statement queryStm = connection.createStatement();
        ResultSet rs = queryStm.executeQuery(
                "select USERNAME_FROM FROM " + tableName + " WHERE USERNAME_TO = \"" + to + "\";");
        while (rs.next()) {
            String friend = rs.getString(1);
            friendRequests.add(friend);
        }
        return friendRequests;
    }
}

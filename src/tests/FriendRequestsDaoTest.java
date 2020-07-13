package tests;

import databases.friends.FriendRequestsDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FriendRequestsDaoTest {
    /* Tests basic functionality of table */
    @Test
    public void test1() throws SQLException {
        FriendRequestsDao dao = new FriendRequestsDao();
        try {
            dao.newFriendshipRequest("adami", "eva");
            dao.newFriendshipRequest("adami", "gveli");
            dao.newFriendshipRequest("chichiko", "bichiko");
            ArrayList<String> friendRequestsList = dao.friendshipRequestsList("adami");
            assertTrue(friendRequestsList.get(0).equals("eva"));
            assertTrue(friendRequestsList.get(1).equals("gveli"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.deleteFriendshipRequest("adami", "eva");
            dao.deleteFriendshipRequest("chichiko", "bichiko");
            dao.deleteFriendshipRequest("adami", "gveli");
        }
    }
}
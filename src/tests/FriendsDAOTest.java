package tests;

import databases.friends.FriendsDAO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FriendsDAOTest {

    /* Tests basic functionality of friends table*/
    @Test
    public void test1() throws SQLException {
        FriendsDAO dao = new FriendsDAO();
        try
        {
            dao.newFriendship("adami", "eva");
            dao.newFriendship("adami", "gveli");
            dao.newFriendship("chichiko", "bichiko");
            ArrayList<String> friendsList = dao.friendsList("adami");
            assertEquals("eva", friendsList.get(0));
            assertEquals("gveli", friendsList.get(1));
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            dao.deleteFriendship("adami", "eva");
            dao.deleteFriendship("chichiko", "bichiko");
            dao.deleteFriendship("adami", "gveli");
        }
    }
}
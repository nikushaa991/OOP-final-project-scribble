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
            dao.add("adami", "eva");
            dao.add("adami", "gveli");
            dao.add("chichiko", "bichiko");
            ArrayList<String> friendsList = dao.toList("adami");
            assertEquals("eva", friendsList.get(0));
            assertEquals("gveli", friendsList.get(1));
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            dao.delete("adami", "eva");
            dao.delete("chichiko", "bichiko");
            dao.delete("adami", "gveli");
        }
    }
}
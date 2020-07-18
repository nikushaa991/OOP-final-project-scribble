package tests;

import databases.users.UsersDAO;
import login.classes.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UsersDAOTest {

    /* Test basic functionality of the database */
    @Test
    public void test1() throws SQLException, NoSuchAlgorithmException {
        String username = "testusername";
        String password = "molly";
        UsersDAO dao = new UsersDAO();
        try {
            dao.newUser(username, password, 1000);
            assertTrue(dao.passwordMatches(username, "molly"));
            assertTrue(dao.exists(username));
            Assertions.assertEquals(dao.nrow() - 1, dao.getUser(username).getId());
            User testUser = dao.getUser(username);
            assertTrue(testUser.getUsername().equals(username));

            dao.updateRank(username, 10000);
            User testUser2 = dao.getUser(username);
            assertEquals(testUser2.getRating(), 10000);
        } finally {
            dao.deleteUser(username);
        }
    }
}
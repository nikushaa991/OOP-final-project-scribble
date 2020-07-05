package login;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UsersDBConnectorTest {

    /* Test basic functionality of the database */
    @Test
    public void test1() throws SQLException, NoSuchAlgorithmException {
        String username = "testusername1";
        String password = "molly";
        UsersDBConnector dao = new UsersDBConnector();
        dao.newUser(username, password);
        assertTrue(dao.passwordMatches(username, "molly"));
        assertTrue(dao.exists(username));
        assertEquals(dao.usersCount()-1, dao.getUser(username).getId());
        dao.deleteUser(username);
    }
}
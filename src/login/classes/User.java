package login.classes;

import databases.users.UsersDAO;

import java.sql.SQLException;

public class User {
    private final int id;
    private final String username;
    private int rating;

    public User(int id, String username, int rating) {
        this.id = id;
        this.username = username;
        this.rating = rating;
    }

    /* getter methods*/
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public void changeRating(int amount, UsersDAO usersDAO) throws SQLException {
        rating += amount;
        rating = Math.min(3000, rating);
        rating = Math.max(0, rating);
        usersDAO.updateRank(username, rating);
    }

}

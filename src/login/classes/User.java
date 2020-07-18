package login.classes;

import databases.users.UsersDAO;

import java.sql.SQLException;

public class User{
    private int id;
    private int rating;
    private String username;
    private String password; //TODO: why store password?

    public User(int id, String username, String password, int rating){
        this.id = id;
        this.username = username;
        this.password = password;
        this.rating = rating;
    }

    /* getter methods*/
    public int getId(){return id;}

    public String getUsername(){return username;}

    public String getPassword(){return password;}

    public int getRating(){return rating;}

    public void changeRating(int amount, UsersDAO usersDAO) throws SQLException {
        rating += amount;
        rating = Math.min(3000, rating);
        rating = Math.max(0, rating);
        usersDAO.updateRank(username, rating);
    }

}

package login.classes;

public class User{
    private int id;
    private int rating;
    private String username;
    private String password; //TODO: why store password?

    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
        this.rating = 1000;
    }

    /* getter methods*/
    public int getId(){return id;}

    public String getUsername(){return username;}

    public String getPassword(){return password;}

    public int getRating(){return rating;}

    public void changeRating(int amount){
        rating += amount;
        rating = Math.min(3000, rating);
        rating = Math.max(0, rating);
    }

}

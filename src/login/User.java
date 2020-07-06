package login;

public class User{
    private int id;
    private String username;
    private String password;
    private int score;

    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
        this.score = 0;
    }

    /* getter methods*/
    public int getId(){return id;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public int getScore(){return score;}

}

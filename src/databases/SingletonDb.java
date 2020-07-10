package databases;

import utils.DatabaseCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import utils.DatabaseCredentials;

public class SingletonDb {
    private static Connection con = null;

    private SingletonDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    DatabaseCredentials.url,
                    DatabaseCredentials.user,
                    DatabaseCredentials.password);
            Statement useDbStm = con.createStatement();
            useDbStm.executeQuery("USE SCRIBBLE;");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance(){
        if(con == null) {
            SingletonDb db = new SingletonDb();
        }
        return con;
    }
}

package databases.friends;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Friends {
    public void add(String to, String from) throws SQLException;
    public void delete(String to, String from) throws SQLException;
    public ArrayList<String> toList(String to) throws SQLException;
}

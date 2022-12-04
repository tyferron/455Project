package csci455.project.chatroom.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Environment {
    public static Connection getConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.postregql.Driver");
        return DriverManager.getConnection("jdbc:posgresql://localhost/ChatroomDB", 
        "postgres", "Ndsu#5973");
    }
}

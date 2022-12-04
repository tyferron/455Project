package csci455.project.chatroom.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import csci455.project.chatroom.server.models.DatabaseCredential;
public class Mapper {
    public static Connection getConnection(DatabaseCredential credential) throws ClassNotFoundException, SQLException
    {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:" + credential.getPortNumber() +
        "/" + credential.getDatabaseName(), credential.getUserName(), credential.getPassword());
    }
}

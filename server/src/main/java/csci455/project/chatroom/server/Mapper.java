package csci455.project.chatroom.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import csci455.project.chatroom.server.models.DatabaseCredential;

public class Mapper {
    public static Connection getConnection(DatabaseCredential credential) 
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:" + credential.getPortNumber() +
                "/" + credential.getDatabaseName(), credential.getUserName(), credential.getPassword());
        }
        catch (Exception ex)
        {
            System.out.println("Unable to connect to the database");
            return null;
        }
    }

    public static ResultSet resultOf(Connection connection, String sql)
    {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Unable to load the Users Table.");
            return null;
        }
    }
}

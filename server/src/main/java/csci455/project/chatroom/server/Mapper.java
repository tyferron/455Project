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
            ex.printStackTrace();
            return null;
        }
    }

    public static int getNextId(Connection connection, String tableName) {
        try 
        {
            int id = 0;
            String sql = "SELECT * FROM public.\"" + tableName + "\"";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet table = statement.executeQuery();
            while (table.next())
            {
                id = table.getInt(1);
            }
            table.close();
            statement.close();
            connection.close();
            return id + 1;
        } 
        catch (SQLException ex) 
        {
            return 0;
        }
    }
}

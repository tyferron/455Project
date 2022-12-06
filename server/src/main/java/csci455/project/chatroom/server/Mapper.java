package csci455.project.chatroom.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

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

    public static String generateString(Random rand, int maxLength)
    {
        int length = rand.nextInt(maxLength) + 1;
        String result = "";
        for (int i = 0; i < length; i++)
        {
            char c;
            do
            {
                c = (char)(rand.nextInt(94) + 33);
            } while(!(c >= '0' && c <= '9') && !(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z'));
            result += c;
        }
        return result;
    }

    public static ResultSet resultOf(Connection connection, String sql)
    {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException ex) {
        	System.err.println(sql);
        	ex.printStackTrace();
            return null;
        }
    }
    
    public static boolean execute(Connection connection, String sql) {
    	try {
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.execute();
        } catch (SQLException ex) {
        	System.err.println(sql);
        	ex.printStackTrace();
            return false;
        }
    }
}

package csci455.project.chatroom.server;

import java.sql.Connection;
import java.sql.SQLException;
import org.postgresql.jdbc.PgConnection;

public class App 
{
    public static void main(String[] args)
    {
        try
        {
            PgConnection conn = new PgConnection(null, null, null)
            Connection c = Environment.getConnection();
            System.out.println("Connection Sucessfull.");
            c.close();
        }
        catch (Exception ex)
        {
            if (ex instanceof ClassNotFoundException)
            {
                System.out.println("Unable to find driver.");
            }
            else if (ex instanceof SQLException)
            {
                System.out.println("Connection Unsuccessfull.");
            }
            else
            {
                System.out.println(ex.getClass().getName());
            }
        }
    }
}

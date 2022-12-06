package csci455.project.chatroom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import csci455.project.chatroom.server.models.DatabaseCredential;

public class Server 
{
	static Map<Integer, String> messageHistory = new HashMap<>();
    static int SERVER_PORT = 29000;
    static Connection conn;
    private static DatabaseCredential credential;
    public static void main(String[] args)
    {
    	boolean close=false;
    	try
        {
    		credential = new DatabaseCredential(7359, "ChatroomDB", 
    		        "postgres", "Ndsu#5973");
    		Connection connection = Mapper.getConnection(credential);
            System.out.println("DB Connection Sucessfull.");
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
            ex.printStackTrace();
        }
        try{
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Opening server on port: "+SERVER_PORT);
            while(true){
                try {
                    Socket client = serverSocket.accept();
                    new ClientConnection(client).start();
                    System.out.println("New connection accepted @ "+client.getInetAddress());
                } catch(Exception e){
                    System.err.println("Error while establishing a new client connection");
                    e.printStackTrace();
                }
                if(close) {
                	serverSocket.close();
                	try {
                		conn.close();
                	} catch (SQLException e) {
                		e.printStackTrace();
                	}
                	break;
                }
            }
        } catch(IOException e){
            System.out.println("Failed to create server.");
            e.printStackTrace();
        }
    }
}
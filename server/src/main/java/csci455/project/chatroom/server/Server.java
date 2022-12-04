package csci455.project.chatroom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class Server 
{
    static int SERVER_PORT = 29000;
    static Connection conn;
    public static void main(String[] args)
    {
    	boolean close=false;
    	try
        {
            conn = Environment.getConnection();
            System.out.println("Connection Sucessfull.");
            conn.close();
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
                	break;
                }
            }
        } catch(IOException e){
            System.out.println("Failed to create server.");
            e.printStackTrace();
        }
    }
}
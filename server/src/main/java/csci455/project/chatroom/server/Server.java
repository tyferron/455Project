package csci455.project.chatroom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import csci455.project.chatroom.server.models.DatabaseCredential;

public class Server 
{
//	static Map<Integer, String> messageHistory = new HashMap<>();
    static int SERVER_PORT = 29001;
    private static Connection conn;
    public static Connection getConn() {
    	if(conn==null) {
    		credential = new DatabaseCredential(5432, "ChatroomDB", 
    		        "postgres", "postgres");
    		conn = Mapper.getConnection(credential);
    		try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return conn;
    }
    static DatabaseCredential credential;
    static ServerSocket serverSocket;
    
    
    public static void main(String[] args)
    {
    	Runtime.getRuntime().addShutdownHook(new Thread()
        {
        	@Override public void run() {
        		try { serverSocket.close(); }
        		catch (IOException e) { e.printStackTrace(); }
        		
        		try { conn.close(); }
        		catch (SQLException e) { e.printStackTrace(); }
        	}
        });
    	try{
        	SERVER_PORT=Integer.parseInt(args[0]);
            serverSocket = new ServerSocket(SERVER_PORT);
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
            }
        } catch(IOException e){
            System.out.println("Failed to create server.");
            e.printStackTrace();
        }
    }
}
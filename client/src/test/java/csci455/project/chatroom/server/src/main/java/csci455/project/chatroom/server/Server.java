package csci455.project.chatroom.server;

import java.io.IOException;
import java.net.*;

public class Server 
{
    static int SERVER_PORT = 29000;
    public static void main(String[] args)
    {
    	boolean close=false;
        //Is an interface needed? or is this just a background process?
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
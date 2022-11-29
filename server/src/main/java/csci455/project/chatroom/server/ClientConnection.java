package csci455.project.chatroom.server;
import java.io.*;
import java.net.*;

public class ClientConnection extends Thread {
    Socket clientSocket;

	InputStream inFromClient;
	OutputStream outToClient;
    byte[] request = new byte[1024];
    public ClientConnection(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
			clientSocket.setSoTimeout(2000);
			inFromClient = clientSocket.getInputStream();
			outToClient = clientSocket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void handleRequest(byte[] request){
        //TODO stubs are great
        /*
        * TODO anticipated requests:
        *   * getMessages(channel)
        *   * sendMessage(channel, message)
        *   * login(username, password)
        *   * etc. Consult design document.
        */
    }

    @Override public void run() {
		try {
			while(inFromClient.read(request) != -1){
                handleRequest(request);
                outToClient.write(request);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}

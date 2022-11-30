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

    static String bytesToString(byte[] bytes){
        StringBuilder s = new StringBuilder();
        for(byte b : bytes){ s.append((char)b); }
        return s.toString();
    }

    static byte[] stringToBytes(String string){
        byte[] res = new byte[string.length()];
        for(int i = 0; i < string.length(); i++){ res[i]=(byte)string.charAt(i); }
        return res;
    }

    private void handleRequest(byte[] request_bytes){
        String[] request=bytesToString(request_bytes).split("\n");
        switch (request[0]) {
            case "GETMESSAGES": //Used for loading, (reloading), and switching chatrooms/DMs
                //TODO request[1] is roomID
                //TODO returns to client message history of specified room
                    //TODO FORMAT:
                    // MESSAGEGET
                    // roomID
                    // history
                break;
            case "SENDMESSAGE":
                //TODO request[1] is roomID, request[2-N] is message (escape newlines so is only [2]?)
                //TODO updates message history of specified room with supplied message (note: practice safe data-handling.) return as if GETMESSAGES was called after message history updated
                break;
            case "LOGIN":
                //TODO request[1] is username, request[2] is password (hashed please)
                //TODO returns whether login attempt was successful. Unsure how to properly process
                break;
            case "CREATEACCOUNT":
                //TODO request[1] is username, request[2] is password (hashed please)
                //TODO returns whether account creation was successful. If successful returns as if successful login.
                break;
            case "JOINROOM": //Specifically adding a chatroom to their room list
                //TODO request[1] is roomID, request[2] is roomPassword if applicable (probably also hashed)
                //TODO returns whether the room was successfully joined. If successful, returns as if GETMESSAGES was called on room.
                //TODO additionally, the user-room table is updated on DB
                break;
            case "LEAVEROOM": //removing a chatroom from their room list
                //TODO request[1] is roomID
                //TODO user-room table is updated on DB
                //TODO something should be returned. What this is I'm unsure. Default page?
                break;
            case "HEARTBEAT": //Unsure if required
                //TODO no additional arguments? Heartbeats may alternatively be accomplished through routine calls of GETMESSAGES
                break;            
            default:
                //TODO error?
        }
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

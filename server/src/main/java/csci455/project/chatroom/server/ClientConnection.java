package csci455.project.chatroom.server;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientConnection extends Thread {
    Socket clientSocket;

    PrintWriter toClientWriter;
    BufferedReader fromClientReader;
    byte[] request = new byte[1024];
    public ClientConnection(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
			clientSocket.setSoTimeout(2000);
            toClientWriter = new PrintWriter(clientSocket.getOutputStream());
            fromClientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void handleRequest(String[] request){
        switch (request[0]) {
            case "GETMESSAGES": //Used for loading, (reloading), and switching chatrooms/DMs
                //FROMCLIENT FORMAT:
                    // GETMESSAGES
                    // roomID
                //TOCLIENT FORMAT:
                    // MESSAGESGOT
                    // roomID
                    // history
                for(String reqLine : getMessages(Integer.parseInt(request[1]))){
                    toClientWriter.println(reqLine);
                }
                toClientWriter.println("END");
                break;
            case "SENDMESSAGE":
                //FROMCLIENT FORMAT:
                    // SENDMESSAGE
                    // roomID
                    // message
                //TOCLIENT: returns a messageGet
                for(String reqLine : sendMessage(Integer.parseInt(request[1]), request[2])){
                    toClientWriter.println(reqLine);
                }
                toClientWriter.println("END");
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

    private String[] getMessages(int roomID){
        return new String[]{"MESSAGESGOT",roomID+"", "TODO"}; //TODO replace todo string with poking the DB 
    }

    private String[] sendMessage(int roomID, String message){
        //TODO *roomID* message history updated with *message*. Remember safe data handling!
        return getMessages(roomID);
    }

    @Override public void run() {
		try {
            String[] request = new String[1];
			while(request[request.length-1]!="END"){
                String[] temp = new String[request.length+1];
                for(int i = 0; i < request.length; i++){
                    temp[i]=request[i];
                }
                request[request.length-1]=fromClientReader.readLine();
			}
            handleRequest(Arrays.copyOf(request, request.length-1));
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
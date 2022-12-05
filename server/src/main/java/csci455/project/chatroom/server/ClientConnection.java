package csci455.project.chatroom.server;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientConnection extends Thread {
    Socket clientSocket;

    PrintWriter toClientWriter;
    BufferedReader fromClientReader;
    
    int userID;
    
    byte[] request = new byte[1024];
    public ClientConnection(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
//			clientSocket.setSoTimeout(2000);
            toClientWriter = new PrintWriter(clientSocket.getOutputStream());
            fromClientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override public void run() {
    	while(true) {
			try {
	            String[] request = new String[0];
				while(request.length==0 || !request[request.length-1].equals("END")){
	                String[] temp = new String[request.length+1];
	                for(int i = 0; i < request.length; i++){
	                    temp[i]=request[i];
	                }
	                temp[temp.length-1]=fromClientReader.readLine();
	                request=temp;
				}
	            handleRequest(Arrays.copyOf(request, request.length-1));
			} catch(IOException e){
				e.printStackTrace();
				System.out.println("It is likely that this occurred due to a client being closed and it is not an issue. If you receive a complaint about the client application crashing, review these log files and contact the devs.");
				return;
			}
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
            		// END
                for(String reqLine : getMessages(Integer.parseInt(request[1]))){
                    toClientWriter.println(reqLine);
                }
                toClientWriter.println("END");
            	toClientWriter.flush();
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
            	toClientWriter.flush();
                break;
            case "LOGIN":
            	//FROMCLIENT FORMAT:
            		// LOGIN
            		// username
            		// hashed password
            	//TOCLIENT:
            		//LOGIN
            		//userID, -1 if error
            		//END
            	toClientWriter.println("LOGIN");
            	userID=testLogin(request[1], request[2]);
            	toClientWriter.println(userID);
            	toClientWriter.println("END");
            	toClientWriter.flush();
                break;
            case "CREATEACCOUNT":
            	//FROMCLIENT FORMAT:
            		// CREATEACCOUNT
            		// username
            		// hashed password
            	//TOCLIENT:
            		//CREATEACCOUNT
            		//userID, -1 if error
            		//END
            	toClientWriter.println("CREATEACCOUNT");
            	userID=createAccount(request[1], request[2]);
            	toClientWriter.println(userID);
            	toClientWriter.println("END");
            	toClientWriter.flush();
                break;
            case "DELETEACCOUNT":
            	//FROMCLIENT FORMAT:
            		// DELETEACCOUNT
            		// username
            		// hashed password
            	//TOCLIENT:
            		//DELETEACCOUNT
            		//END
            	toClientWriter.println("DELETEACCOUNT");
            	toClientWriter.println("END");
            	toClientWriter.flush();
                break;
            case "JOINROOM": //Specifically adding a chatroom to their room list
            	//FROMCLIENT FORMAT:
            		//JOINROOM
            		//roomID
            		//password (or blank)
            		//END
            	//TOCLIENT:
            		//ROOMJOINED
            		//roomID
            		//true/false - false if bad password or bad room id
            		//END
                toClientWriter.println("ROOMJOINED");
                toClientWriter.println(request[1]);
                toClientWriter.println(joinRoom(request[1], request[2]));
                toClientWriter.println("END");
            	toClientWriter.flush();
            	for(String reqLine : getMessages(Integer.parseInt(request[1]))){
                    toClientWriter.println(reqLine);
                }
                toClientWriter.println("END");
            	toClientWriter.flush();
                break;
            case "CREATEROOM": //Specifically adding a chatroom to their room list
            	//FROMCLIENT FORMAT:
            		//CREATEROOM
            		//roomName
            		//password (or blank)
            		//END
            	//TOCLIENT:
            		//RoomJoined return
                toClientWriter.println("ROOMJOINED");
                toClientWriter.println(request[1]);
                toClientWriter.println(joinRoom(request[1], request[2]));
                toClientWriter.println("END");
            	toClientWriter.flush();
            	for(String reqLine : getMessages(Integer.parseInt(request[1]))){
                    toClientWriter.println(reqLine);
                }
                toClientWriter.println("END");
            	toClientWriter.flush();
                break;
            case "LEAVEROOM": //removing a chatroom from their room list
            	//FROMCLIENT FORMAT:
            		//LEAVEROOM
            		//roomID
            		//END
            	//TOCLIENT:
            		//ROOMLEFT
            		//roomID
            		//END
            	leaveRoom(request[1]);
                toClientWriter.println("ROOMLEFT");
                toClientWriter.println(request[1]);
                toClientWriter.println("END");
            	toClientWriter.flush();
                break;
            case "DELETEROOM": //removing a chatroom from their room list
            	//FROMCLIENT FORMAT:
            		//LEAVEROOM
            		//roomID
            		//END
            	//TOCLIENT:
            		//ROOMDELETED
            		//roomID
            		//END
            	leaveRoom(request[1]);
                toClientWriter.println("ROOMLEFT");
                toClientWriter.println(request[1]);
                toClientWriter.println("END");
            	toClientWriter.flush();
                break;
            case "LISTROOMS": //listing joined rooms
            	//FROMCLIENT FORMAT:
            		//LISTROOMS
            		//END
            	//TOCLIENT
            		//ROOMSLIST
            		//roomID
            		//roomName
            		//repeat previous 2 lines for whole list
            		//END
                toClientWriter.println("ROOMSLIST");
                for(String s : listJoinedRooms()) { toClientWriter.println(s); }
                toClientWriter.println("END");
            	toClientWriter.flush();
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
    
    private int testLogin(String username, String hashedPass) {
		return -1; //TODO return userid if valid, -1 if an error was encountered (username DNE or pass doesn't match)
    }
	private int createAccount(String username, String hashedPass) {
		//TODO create the account
		return -1; //TODO return userID if created, -1 if an error was encountered (likely username already exists)
	}
	private boolean joinRoom(String roomID, String hashedPass) {
		//TODO update user-room table
		return true; //TODO return true if joined, false if an error was encountered (room DNE or pass doesn't match.) NOTE: any pass accepted for room without pass
	}
	private String[] listJoinedRooms() {
		String[] rooms = new String[] {};
		//TODO fill rooms with a list of joined rooms
		//TODO it should be an even number of strings, each an id followed by a room name
		return rooms;
	}
	private void leaveRoom(String roomID) {
		//TODO update user-room table
	}
}

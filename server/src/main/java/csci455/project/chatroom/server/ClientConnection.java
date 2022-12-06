package csci455.project.chatroom.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import csci455.project.chatroom.server.collections.ChatRoomCollection;
import csci455.project.chatroom.server.collections.UserCollection;
import csci455.project.chatroom.server.models.ChatRoom;
import csci455.project.chatroom.server.models.User;

public class ClientConnection extends Thread {
    Socket clientSocket;

    PrintWriter toClientWriter;
    BufferedReader fromClientReader;
    
    int userID;
    
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
            		//userName, empty if error
            		//END
            	toClientWriter.println("LOGIN");
            	userID=testLogin(request[1], request[2]);
            	if(userID>0) {
            		toClientWriter.println(new UserCollection(Server.credential).get(userID).getUserName());
            	} else {
            		toClientWriter.println();
            	}
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
            	if(userID>0) {
            		toClientWriter.println(new UserCollection(Server.credential).get(userID).getUserName());
            	} else {
            		toClientWriter.println();
            	}
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
    	ChatRoomCollection roomTable = new ChatRoomCollection(Server.credential);
    	System.out.println(roomID);
    	if(roomTable.get(roomID).getMessageHistory()==null||roomTable.get(roomID).getMessageHistory().trim().equals("")) {
    		return new String[] {"MESSAGESGOT", roomID+""};
    	}
    	String[] hist = roomTable.get(roomID).getMessageHistory().split("\n");
    	String[] result = new String[2+hist.length];
        result[0]="MESSAGESGOT";
        result[1]=roomID+"";
        for(int i = 0; i < hist.length; i++) {
        	result[i+2]=hist[i];
        }
    	return result;
    }

    private String[] sendMessage(int roomID, String message){
    	ResultSet result = Mapper.resultOf(Server.getConn(), "SELECT * FROM public.\"ChatRoom\" WHERE \"RoomID\" = " + roomID + ";");
    	try {
			ChatRoom room = result.next() ? new ChatRoom(result.getInt(1), result.getString(2), result.getString(3), result.getString(4)) : null;
			if(room==null) { return null; }
			String newHistory = room.getMessageHistory();
			if(newHistory==null||newHistory.trim().equals("null")||newHistory.trim().equals("")) { newHistory = message; }
			else { newHistory+="\n" + message; }
			Mapper.execute(Server.getConn(), "UPDATE public.\"ChatRoom\" SET \"MessageHistory\"='"+newHistory+"' WHERE \"RoomID\" = " + roomID + ";");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
        return getMessages(roomID);
    }
    
    private int testLogin(String username, String hashedPass) {
    	ResultSet result = Mapper.resultOf(Server.getConn(), "SELECT * FROM public.\"Users\" WHERE \"UserName\" = '" + username + "';");
    	try {
			User user = result.next() ? new User(result.getInt(1), result.getString(2), result.getString(3)) : null;
			System.out.println(user);
			if(user==null) { return -1; }
			if(user.getPassword().equals(hashedPass)) {
				return user.getKey();
			} else {
				return -1;
			}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    }
	private int createAccount(String username, String hashedPass) {
		ResultSet result = Mapper.resultOf(Server.getConn(), "SELECT * FROM public.\"Users\" WHERE \"UserName\" = '" + username + "';");
		try {
			User user = result.next() ? new User(result.getInt(1), result.getString(2), result.getString(3)) : null;
			System.out.println(user);
			System.out.println(result);
			if(user!=null) { return -1; }
			Mapper.execute(Server.getConn(), "INSERT INTO public.\"Users\" (\"UserName\", \"Password\") VALUES('"+username+"','"+hashedPass+"')");
			return testLogin(username, hashedPass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
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

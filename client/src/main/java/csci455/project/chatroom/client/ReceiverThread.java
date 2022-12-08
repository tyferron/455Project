package csci455.project.chatroom.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReceiverThread extends Thread {
	
	
	@Override public void run() {
		while(true){
        	try {
        	    List<String> response= new ArrayList<String>();
                response.add(Client.in.readLine());
                while(!response.get(response.size()-1).equals("END")){ response.add(Client.in.readLine()); } 
                response.remove(response.size()-1);
                handleResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	private static void handleResponse(List<String> response) {
    	switch(response.get(0)) {
    	case "MESSAGESGOT":

    		int roomID = Integer.parseInt(response.get(1));
    		response.remove(1);
    		response.remove(0);
    		Client.gui.setReceivedMessages(roomID, response);
//    		if(Client.roomID==Integer.parseInt(response.get(1))) {
//    			for(int i = 2; i < response.size(); i++) {
//    				System.out.println(response.get(i));
//    			}
//    		}
    		break;
    	case "LOGIN":
    		System.out.println(response.get(1));
    		Client.username=response.get(1);
    		System.out.println(Client.username);
//    		if(Integer.parseInt(response.get(1))== -1) {
//    			System.out.println("Login unsuccessful!");
//    			Client.gui.setUserID(MAX_PRIORITY);
//    		} else {
//    			System.out.println("Login successful!");
//    		}
    		break;
    	case "CREATEACCOUNT":
    		Client.username=response.get(1);
//    		if(Integer.parseInt(response.get(1))== -1) {
//    			System.out.println("Could not create account");
//    		} else {
//    			System.out.println("Account created");
//    		}
    		break;
    	case "ROOMJOINED":
    		Client.gui.setJoinedRoom(response.get(2).equals("true"), Integer.parseInt(response.get(1)));
    		Client.gui.messagesView.removeAll();
//    		if(response.get(3).equals(true)) {
//    			System.out.println("Room joined");
//    		} else {
//    			System.out.println("Error occured when joining room");
//    		
//    		}
    		break;
    	case "ROOMLEFT":
    		Client.gui.setLeaveRoom(Integer.parseInt(response.get(1)));
//    		if(Client.roomID==Integer.parseInt(response.get(1))) {
//    			System.out.println("Leaving room "+ Client.roomID );
//    		}
    		break;
    	case "ROOMCREATED":
    		Client.roomID=Integer.parseInt(response.get(1));
    		Client.gui.messagesView.removeAll();
    		Client.gui.createRoomWindow.dispose();
    		Client.gui.createRoomWindow=null;
    		Client.getMessages();
    		break;
    	case "LISTROOMS":
    		response.remove(0);
    		Client.gui.setRoomsList(response);
//    		for(int i = 2; i < response.size(); i++) {
//				System.out.println("ID: "+response.get(i++)+", Name: "+response.get(i));
//			}
    		break;
    	}
	}
}

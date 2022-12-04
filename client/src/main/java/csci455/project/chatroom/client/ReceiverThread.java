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
	private static boolean handleResponse(List<String> response) {
    	switch(response.get(0)) {
    	case "CREATEACCOUNT":
    		if(Client.roomID==Integer.parseInt(response.get(1))) {
    			return true;
    		}
    		return false;
    	case "ROOMJOINED":
    		if(Client.roomID==Integer.parseInt(response.get(1))) {
    			return response.get(3).equals(true);
    		}
    		return false;
    	case "LISTROOMS":
    		return true;
    	case "MESSAGESGOT":

    		int roomID = Integer.parseInt(request.get(1));
    		request.remove(1);
    		request.remove(0);
    		Client.gui.setReceivedMessages(roomID, request);
//    		if(Client.roomID==Integer.parseInt(request.get(1))) {
//    			for(int i = 2; i < request.size(); i++) {
//    				System.out.println(request.get(i));
//    			}
//    		}
    		break;
    	case "LOGIN":
    		Client.gui.setLogin(Integer.parseInt(request.get(1)));
//    		if(Integer.parseInt(request.get(1))== -1) {
//    			System.out.println("Login unsuccessful!");
//    			Client.gui.setUserID(MAX_PRIORITY);
//    		} else {
//    			System.out.println("Login successful!");
//    		}
    	case "CREATEACCOUNT":
    		Client.gui.setLogin(Integer.parseInt(request.get(1)));
//    		if(Integer.parseInt(request.get(1))== -1) {
//    			System.out.println("Could not create account");
//    		} else {
//    			System.out.println("Account created");
//    		}
    		
    	case "ROOMJOINED":
    		Client.gui.setJoinedRoom(request.get(2).equals(true), Integer.parseInt(request.get(1)));
//    		if(request.get(3).equals(true)) {
//    			System.out.println("Room joined");
//    		} else {
//    			System.out.println("Error occured when joining room");
//    		
//    		}
    	case "ROOMLEFT":
    		Client.gui.setLeaveRoom(Integer.parseInt(request.get(1)));
//    		if(Client.roomID==Integer.parseInt(request.get(1))) {
//    			System.out.println("Leaving room "+ Client.roomID );
//    		}
    		
    	case "LISTROOMS":
    		request.remove(0);
    		Client.gui.setRoomsList(request);
//    		for(int i = 2; i < request.size(); i++) {
//				System.out.println("ID: "+request.get(i++)+", Name: "+request.get(i));
//			}
    		
    	

    	} 
    	
    	return false;
    }
	}
}

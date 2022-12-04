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
    		return true;
    	case "LOGIN":
    		if(Client.roomID==Integer.parseInt(response.get(1))) {
    			return true;
    		}
    	} 
    	
    	return false;
    }

}

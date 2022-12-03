package csci455.project.chatroom.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReceiverThread extends Thread {
	
	
	@Override public void run() {
		while(true){
        	try {
        	    List<String> request= new ArrayList<String>();
                request.add(Client.in.readLine());
                while(!request.get(request.size()-1).equals("END")){ request.add(Client.in.readLine()); } 
                request.remove(request.size()-1);
                handleRequest(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	static private void handleRequest(List<String> request) {
    	switch(request.get(0)) {
    	case "MESSAGESGOT":
    		if(Client.roomID==Integer.parseInt(request.get(1))) {
    			for(int i = 2; i < request.size(); i++) {
    				System.out.println(request.get(i));
    			}
    		}
    		break;
    	}
    }

}

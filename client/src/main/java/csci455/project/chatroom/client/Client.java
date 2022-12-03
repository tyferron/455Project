package csci455.project.chatroom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import csci455.project.chatroom.client.GUI.GUI;

public class Client {
    static BufferedReader in;
    static PrintWriter out;
    static int roomID = 1234; //ID of current room
    static int SERVER_PORT = 29000;
    final static Scanner sc = new Scanner(System.in);
    static GUI gui;
    public static void main(String[] args) {
    	gui=new GUI();
    	gui.run();
    	boolean close=false;
        try {
            Socket clientSocket = new Socket("127.0.0.1", SERVER_PORT); //loop address
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            try {
//                Thread sender = new Thread(new Runnable() {
//                    public void run() {
//                        while(true){
//                            sendMsg(sc.nextLine());
//                        }
//                    }
//                });
//                sender.start();
                Thread receiver = new Thread(new Runnable() {
                    public void run(){
                        while(true){
                        	try {
                        	    List<String> request= new ArrayList<String>();
	                            request.add(in.readLine());
	                            while(!request.get(request.size()-1).equals("END")){ request.add(in.readLine()); } 
	                            request.remove(request.size()-1);
	                            handleRequest(request);
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
                        }
                    }
                });
                receiver.start();
                Thread messageGetterThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						while(true) {
							try {
								Thread.sleep(1000);
								getMessages();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				});
                messageGetterThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(close) {
            	clientSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        
        
    

   }

    static private void handleRequest(List<String> request) {
    	switch(request.get(0)) {
    	case "MESSAGESGOT":
    		if(roomID==Integer.parseInt(request.get(1))) {
    			for(int i = 2; i < request.size(); i++) {
    				System.out.println(request.get(i));
    			}
    		}
    		break;
    	}
    }
    
    static public void sendMsg(String msg) {
//    	System.out.println("Sending message: "+msg);
    	out.println("SENDMESSAGE");
    	out.println(roomID);
        out.println(msg);
        out.println("END");
        out.flush();
    }

    static public void getMessages() {
    	out.println("GETMESSAGES");
    	out.println(roomID);
        out.println("END");
        out.flush();
    }

    public void changeRoom(int roomID) {
    	Client.roomID = roomID;
    	getMessages();
    }

    public String[] getChatRooms(){
    	//do we want this to display all the rooms
        return null;
    }
    
    public boolean createAccount(String username, String password) {
    	
    	password = hashString(password);
    	
    	System.out.println("Creating Account: "+username);
    	out.println("CREATEACCOUNT");
    	out.println(username);
        out.println(password);
        out.println("END");
        out.flush();
        
        //TODO: return response from server
        return true;
    }
    
    public boolean deleteAccount(String username, String password) {
    	
    	password = hashString(password);
    	
    	System.out.println("Creating Account: "+username);
    	out.println("DELETEACCOUNT");
    	out.println(username);
        out.println(password);
        out.println("END");
        out.flush();
        
        //TODO: return response from server
        return true;
    	
    }

    public boolean login(String username, String password) {
    	
    	password = hashString(password);
    	
    	System.out.println("Logging in: "+username);
    	out.println("LOGIN");
    	out.println(username);
        out.println(password);
        out.println("END");
        out.flush();
        
        //TODO: return response from server
        return true;
    }

    public boolean createChatRoom(int roomID, String password) {
    	
    	password = hashString(password);
    	
    	System.out.println("Creating room: "+roomID);
    	out.println("CREATEROOM");
    	out.println(roomID);
        out.println(password);
        out.println("END");
        out.flush();
        
        //TODO: return response from server
        return true;
    }
    
    public boolean deleteChatRoom(int roomID, String password) {

    	password = hashString(password);
    	
    	System.out.println("Deleting room: "+roomID);
    	out.println("DELETEROOM");
    	out.println(roomID);
        out.println(password);
        out.println("END");
        out.flush();
        
        //TODO: return response from server
    	return true;
    }

    public boolean joinChatRoom(int roomID, String password) {

    	password = hashString(password);
    	
    	System.out.println("Joining Room: "+roomID);
    	out.println("JOINROOM");
    	out.println(roomID);
        out.println(password);
        out.println("END");
        out.flush();
        
        //TODO: return response from server
    	return true;
    }

    public boolean leaveChatRoom(int roomID) {
    	
    	System.out.println("Leaving Room: "+roomID);
    	out.println("CREATEROOM");
    	out.println(roomID);
        out.println("END");
        out.flush();
        
        //TODO: return response from server
        return true;
    }
    
    private static String hashString(String password) {
    	MessageDigest md;
    	try
        {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");

            // Generate the random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            // Passing the salt to the digest for the computation
            md.update(salt);
            // Generate the salted hash
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));
            System.out.println(sb);
            return sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // TODO alert if error
            return null;
        }
    }
    
}
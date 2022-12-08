package csci455.project.chatroom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import csci455.project.chatroom.client.GUI.GUI;
import csci455.project.chatroom.client.GUI.Login;

public class Client {
    static BufferedReader in;
    static PrintWriter out;
    public static int roomID = 1234; //ID of current room
    static int SERVER_PORT = 29000;
    final static Scanner sc = new Scanner(System.in);
    static GUI gui;
    public static String username="Nick";
    public static List<JFrame> loginWindows = new ArrayList<>();
    public static void main(String[] args) {

    	Login login = new Login();
    	loginWindows.add(login);
    	username = login.getUsername();
//    	gui=new GUI();
//    	gui.run();
    	boolean close=false;
        try {
            Socket clientSocket = new Socket(args[0], SERVER_PORT); //loop address
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
                Thread receiver = new ReceiverThread();
                receiver.start();

            	System.out.println("Pre");
            	

            	while(username.equals("")) {
            		System.out.println(username);
            		continue;
            	}
            	for(JFrame j : loginWindows) { j.dispose(); }
            	System.out.println("Past");
            	gui=new GUI();
            	gui.run();
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

    
    
    static public void sendMsg(String msg) {
//    	System.out.println("Sending message: "+msg);
    	out.println("SENDMESSAGE");
    	out.println(roomID);
        out.println(username+":"+msg);
        out.println("END");
        out.flush();
    }

    static public void getMessages() {
    	out.println("GETMESSAGES");
    	out.println(roomID);
        out.println("END");
        out.flush();
    }

    static public void changeRoom(int roomID) {
    	Client.roomID = roomID;
    	getMessages();
    }


    public static void getChatRooms(){

    	System.out.println("Getting Chatrooms");
    	out.println("LISTROOMS");
        out.println("END");
        out.flush();

    }
    

    
    public static void createAccount(String username, String password) {

    	
    	password = hashString(password);
    	
    	System.out.println("Creating Account: "+username);
    	out.println("CREATEACCOUNT");
    	out.println(username);
        out.println(password);
        out.println("END");
        out.flush();
        
        //TODO: return response from server
    }
    

    
    public static void deleteAccount(String username, String password) {
    	
    	password = hashString(password);
    	
    	System.out.println("Creating Account: "+username);
    	out.println("DELETEACCOUNT");
    	out.println(username);
        out.println(password);
        out.println("END");
        out.flush();
        
    }


    public static void login(String username, String password) {

    	
    	password = hashString(password);
    	
    	System.out.println("Logging in: "+username);
    	out.println("LOGIN");
    	out.println(username);
        out.println(password);
        out.println("END");
        out.flush();
        
    }

    public static void createChatRoom(String roomName, String password) {

    	
    	password = hashString(password);
    	
    	System.out.println("Creating room: "+roomName);
    	out.println("CREATEROOM");
    	out.println(roomName);
        out.println(password);
        out.println("END");
        out.flush();
        
    }
    

    public static void deleteChatRoom(int roomID) {
    	
    	System.out.println("Deleting room: "+roomID);
    	out.println("DELETEROOM");
    	out.println(roomID);
        out.println("END");
        out.flush();
    }


    public static void joinChatRoom(int roomID, String password) {

    	password = hashString(password==null?"":password);
    	
    	System.out.println("Joining Room: "+roomID);
    	out.println("JOINROOM");
    	out.println(roomID);
        out.println(password);
        out.println("END");
        out.flush();

    }

    static public void leaveChatRoom(int roomID) {
    	
    	System.out.println("Leaving Room: "+roomID);
    	out.println("LEAVEROOM");
    	out.println(roomID);
        out.println("END");
        out.flush();
    }
    
    private static String hashString(String password) {
    	if(password==null||password.trim().equals("")) { return ""; }
    	MessageDigest md;
    	try
        {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");

            // Generate the random salt
//            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            //random.nextBytes(salt);
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
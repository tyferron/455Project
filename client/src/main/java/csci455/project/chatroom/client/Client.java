package csci455.project.chatroom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static BufferedReader in;
    static PrintWriter out;
    static int roomID = 1234; //ID of current room
    static int SERVER_PORT = 29000;
    final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
    	boolean close=false;
        try {
            Socket clientSocket = new Socket("127.0.0.1", SERVER_PORT);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            try {
                Thread sender = new Thread(new Runnable() {
                    public void run() {
                        while(true){
                            sendMsg(sc.nextLine());
                        }
                    }
                });
                sender.start();
                Thread receiver = new Thread(new Runnable() {
                    String msg;
                    public void run(){
                        try {
                            msg = in.readLine();
                            while(msg!=null){
                                System.out.println("Server :"+msg);
                                msg = in.readLine();
                            } 

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                receiver.start();
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
    	System.out.println("Sending message: "+msg);
    	out.println("SENDMESSAGE");
    	out.println(roomID);
        out.println(msg);
        out.println("END");
        out.flush();
    }

    public String receiveMsg() {
        return null;
    }

    public void changeRoom(String room) {

    }

    public String[] getChatRooms(){
        return null;
    }

    public boolean login(String username, String password) {
        return true;
    }

    public void createChatRoom(String name) {
        
    }

    public void joinChatRoom(String name) {

    }

    public void leaveChatRoom(String name) {
        
    }
    
}
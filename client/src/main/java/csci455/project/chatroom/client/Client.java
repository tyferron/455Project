package csci455.project.chatroom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket clientSocket;
    static BufferedReader in;
    static PrintWriter out;
    final static Scanner sc = new Scanner(System.in);
        
    public Client(){
        try {
            clientSocket = new Socket("127.0.0.1", 5000);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        
        try {
            

            Thread sender = new Thread(new Runnable() {
                String msg;

                
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        out.println(msg);
                        out.flush();
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
    

   }

    public void sendMsg(String msg) {
        
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
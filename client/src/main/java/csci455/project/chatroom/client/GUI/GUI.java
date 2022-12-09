/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci455.project.chatroom.client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import csci455.project.chatroom.client.Client;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author RLiebsch
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	private int userID;

  
    public NewChat createRoomWindow=null;
    public GUI() {
//        log = new NewChat(this);
//    	log.setVisible(true);
    	initComponents();
        messagesView.setLayout(new MigLayout("fillx"));
    }


    public void setUserID(int id) {
    	this.userID = id;
    }
    
    public void setRoomID(int id) {
    	Client.roomID = id;
    }
    
    public void setReceivedMessages(int roomID, List<String>msgs ) {
    	int i = 0;
    	if(Client.roomID==roomID) {
		i = messagesView.getComponentCount();
    	while(i < msgs.size()) {
    		String message = msgs.get(i);
    		System.out.println(message);
    		int split = message.indexOf(':');
    		String user = message.substring(0, split);
    		String msg = message.substring(split+1);
    		if(user.equals(Client.username)) {
        		logOwnMessage(msg);
    		} else {
        		logOtherMessage(user, msg);    			
    		}
    		i++;
    	}
    	messagesView.revalidate();
    	int height = (int)messagesView.getPreferredSize().getHeight();
    	Rectangle rect = new Rectangle(0,height,10,10);
    	messagesView.scrollRectToVisible(rect);
        messagesView.repaint();
        messagesView.revalidate();
    	}
    	//do what you want with the list of messages here
    }
    
    public void setLogin(int userID) {
    	if (userID == -1) {
    		//failed to login
    	} else {
    		this.userID = userID;
    	}
    }
    
    public void setAccountCreate(int userID) {
    	if (userID == -1) {
    		//failed to create account
    	} else {
    		this.userID = userID;
    	}
    }
    
    public void setJoinedRoom(boolean result, int roomID) {
    	if (result) {
    		//room joined
    		Client.roomID = roomID;
    	} else {
    		//failed to join room
    	}
    }
    
    public void setLeaveRoom(int roomID) {
    	//leave room
    }
    public void setRoomsList(List<String> rooms) {
    	//Do what you want with the rooms list here
    }
    
    public void callReceiveMessages() {
    	Client.getMessages();
    }
    
    public void callLogin(String username, String password) {
    	Client.login(username, password);
    }
    
    public void callCreateAccount(String userName, String password) {
    	Client.createAccount(userName, password);
    }
    
    public void callJoinRoom(int roomID, String password) {
    	Client.joinChatRoom(roomID, password);
    }
    
    public void callLeaveRoom(int roomID) {
    	Client.leaveChatRoom(roomID);
    }
    
    public void callChangeRoom(int roomID) {
    	Client.changeRoom(roomID);
    }
    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jScrollPane2 = new JScrollPane();
        txt = new JTextArea();
        sendMessageButton = new JButton();
        jScrollPane1 = new JScrollPane();
        messagesView = new JPanel();
        jScrollPane3 = new JScrollPane();
        jPanel2 = new JPanel();
        jLabel1 = new JLabel();
        createChatRoomButton = new JButton();
        chatRoomListPanel = new JPanel();
        jLabel4 = new JLabel();
        jPanel3 = new JPanel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new Dimension(500, 721));
        setPreferredSize(new Dimension(800, 1421));
        setSize(new Dimension(0, 0));

        jPanel1.setBackground(new Color(0, 0, 102));
        jPanel1.setMaximumSize(new Dimension(32767, 1500));
        jPanel1.setPreferredSize(new Dimension(800, 1421));

        txt.setColumns(20);
        txt.setRows(5);
        jScrollPane2.setViewportView(txt);

        sendMessageButton.setText("Send");
        sendMessageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cmdLeftActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        messagesView.setBackground(new Color(0, 0, 0));
        messagesView.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, null, Color.darkGray, null, Color.gray));

        GroupLayout panelLayout = new GroupLayout(messagesView);
        messagesView.setLayout(panelLayout);
        
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1517, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 659, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(messagesView);

        jPanel2.setBackground(new Color(0, 102, 255));
        jPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, null, Color.gray, null, null));

        jLabel1.setFont(new Font("Arial Black", 1, 14)); // NOI18N
        jLabel1.setText("Chats");

        createChatRoomButton.setText("Create a new chat room");
        createChatRoomButton.setToolTipText("");
        createChatRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createChatroom(evt);
            }
        });

        chatRoomListPanel.setBackground(new Color(0, 102, 255));
        chatRoomListPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        jLabel4.setFont(new Font("sansserif", 0, 18)); // NOI18N
        jLabel4.setText("Joe");

        GroupLayout jPanel4Layout = new GroupLayout(chatRoomListPanel);
        chatRoomListPanel.setLayout(jPanel4Layout);

        jPanel4Layout.setHorizontalGroup(
        		jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
        		jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
//        jPanel4Layout.setHorizontalGroup(
//            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel4Layout.createSequentialGroup()
//                .addGap(21, 21, 21)
//                .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(23, Short.MAX_VALUE))
//        );
//        jPanel4Layout.setVerticalGroup(
//            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel4Layout.createSequentialGroup()
//                .addGap(18, 18, 18)
//                .addComponent(jLabel4)
//                .addContainerGap(507, Short.MAX_VALUE))
//        );
        JButton chatRoomOptionButton = new JButton();
        chatRoomOptionButton.setText("I'm a room");
        chatRoomListPanel.add(chatRoomOptionButton, "wrap, w 80%");
        chatRoomListPanel.repaint();
        chatRoomListPanel.revalidate();

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1)
                        .addGap(72, 72, 72)
                        .addComponent(createChatRoomButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(chatRoomListPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(createChatRoomButton)
                    .addComponent(jLabel1))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chatRoomListPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(375, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel2);

        jPanel3.setBackground(new Color(0, 102, 255));
        jPanel3.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 5));

        jLabel2.setFont(new Font("sansserif", 0, 24)); // NOI18N
        jLabel2.setText("Chatroom: "+Client.roomID);
        jLabel2.setForeground(new Color(222, 222, 222));
        jLabel2.setBorder(BorderFactory.createEmptyBorder());

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(486, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel3.setFont(new Font("sansserif", 0, 24)); // NOI18N
        jLabel3.setText("TechnoColor Chat");
        jLabel3.setForeground(new Color(222, 222, 222));
        jLabel3.setBorder(BorderFactory.createEmptyBorder());

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 749, GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(sendMessageButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 959, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(33, 33, 33)
                    .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(945, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(113, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(sendMessageButton, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 657, GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80))))
            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(739, Short.MAX_VALUE)))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 1421, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLeftActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cmdLeftActionPerformed
        String text = txt.getText().trim();
        txt.setText("");
        if(!text.equals("")) {
            Client.sendMsg(text);        	
        }
    }//GEN-LAST:event_cmdLeftActionPerformed
    
    private void logOwnMessage(String message) {
    	Item_Left item = new Item_Left(Client.username, message);
        messagesView.add(item, "wrap, w 80%");
    }
    
    private void logOtherMessage(String user, String message) {
    	Item_Right item = new Item_Right(user, message);
        messagesView.add(item, "wrap, w 80%");
    }

    private void createChatroom(ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        createRoomWindow = new NewChat(this);
        createRoomWindow.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public void run() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
        GUI me=this;
        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                me.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton sendMessageButton;
    private JButton createChatRoomButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel chatRoomListPanel;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    public JPanel messagesView;
    private JTextArea txt;
    // End of variables declaration//GEN-END:variables
}

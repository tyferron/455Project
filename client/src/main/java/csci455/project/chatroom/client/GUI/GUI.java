/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci455.project.chatroom.client.GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import csci455.project.chatroom.client.Client;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author RLiebsch
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	private int userID;
	private int roomID;
	private String username;

  
    public GUI() {
    	initComponents();
        panel.setLayout(new MigLayout("fillx"));
    }

    public void setUserID(int id) {
    	this.userID = id;
    }
    
    public void setRoomID(int id) {
    	this.roomID = id;
    }
    
    public void setReceivedMessages(int roomID, List<String>msgs ) {
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
    		this.roomID = roomID;
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
        jScrollPane1 = new JScrollPane();
        panel = new JPanel();
        jScrollPane2 = new JScrollPane();
        txt = new JTextArea();
        cmdLeft = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new Color(50, 166, 168));

        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setBackground(new Color(100, 179, 182));

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1051, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panel);

        txt.setColumns(20);
        txt.setRows(5);
        txt.setText("");
        jScrollPane2.setViewportView(txt);

        cmdLeft.setText("Send");
        cmdLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cmdLeftActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdLeft, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdLeft))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLeftActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cmdLeftActionPerformed
        String text = txt.getText().trim();
        txt.setText("");
        Client.sendMsg(text);
        Item_Left item = new Item_Left(text);
        panel.add(item, "wrap, w 80%");
        panel.repaint();
        panel.revalidate();
    }//GEN-LAST:event_cmdLeftActionPerformed

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
    private JButton cmdLeft;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JPanel panel;
    private JTextArea txt;
    // End of variables declaration//GEN-END:variables
}

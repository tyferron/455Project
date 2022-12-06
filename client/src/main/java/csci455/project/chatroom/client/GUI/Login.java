
package csci455.project.chatroom.client.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import csci455.project.chatroom.client.Client;
@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener{
	JPanel panel;
	   JLabel user_label, password_label, message;
	   JTextField userName_text;
	   JPasswordField password_text;
	   JButton submit, cancel, create;
	   private String username = "";
	   public Login() {
	      // Username Label
	      user_label = new JLabel();
	      user_label.setText("User Name :");
	      userName_text = new JTextField();
	      // Password Label
	      password_label = new JLabel();
	      password_label.setText("Password :");
	      password_text = new JPasswordField();
	      // Submit
	      submit = new JButton("SUBMIT");
	      create = new JButton("Create Account");
	      panel = new JPanel(new GridLayout(4, 2));
	      panel.add(user_label);
	      panel.add(userName_text);
	      panel.add(password_label);
	      panel.add(password_text);
	      message = new JLabel();
	      panel.add(message);
	      panel.add(submit);
	      panel.add(create);
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      // Adding the listeners to components..
	      submit.addActionListener(this);
	      create.addActionListener(this);
	      add(panel, BorderLayout.CENTER);
	      setTitle("Please Login Here !");
	      setSize(450,350);
	      setVisible(true);
	   }
	   
	   public String getUsername() {
		   return username;
	   }
	   @Override
	   public void actionPerformed(ActionEvent ae) {
		   if(ae.getSource() == create) {
			   Create create = new Create();
			   create.setVisible(true);

			   Client.loginWindows.add(create);
		   }
		   else if (ae.getSource() == submit) {
			   String userName = userName_text.getText();
			   String password = String.valueOf(password_text.getPassword());
			   Client.login(userName, password);
		   }
	   }
}
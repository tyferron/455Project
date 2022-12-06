
package csci455.project.chatroom.client.GUI;

import csci455.project.chatroom.client.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Login extends JFrame implements ActionListener{
	JPanel panel;
	   JLabel user_label, password_label, message;
	   JTextField userName_text;
	   JPasswordField password_text;
	   JButton submit, cancel, create;
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
	   public static void main(String[] args) {
		      new Login();
	   }
	   @Override
	   public void actionPerformed(ActionEvent ae) {
		   String userName = userName_text.getText();
		   String password = password_text.getText();
		   
		   if(ae.getSource() == create) {
			   Create create = new Create();
			   create.setVisible(true);
			   
		   }
		   else if (ae.getSource() == submit) {
			   //Client.login(userName, password);
			   if(userName.equals("Nick") && password.equals("password")) {
				   GUI gui=new GUI();
				   gui.run();
			   }
		   }
	   }
}
package csci455.project.chatroom.client.GUI;
import csci455.project.chatroom.client.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Create extends JFrame implements ActionListener{
	JPanel panel;
	JLabel user_label, password_label, message, conf_password_label;
	JTextField userName_text;
	JPasswordField password_text, conf_password_text;
	JButton submit, cancel, create;
	Client temp = new Client();
	Create() {
	    // Username Label
		user_label = new JLabel();
	   	user_label.setText("User Name :");
	    userName_text = new JTextField();
	    // Password Label
	    password_label = new JLabel();
	    password_label.setText("Password :");
	    password_text = new JPasswordField();
	    // Confirm Password
	    conf_password_label = new JLabel();
	    conf_password_label.setText("Password :");
	    conf_password_text = new JPasswordField();
	    create = new JButton("Create");
	    panel = new JPanel(new GridLayout(4, 2));
	    panel.add(user_label);
	    panel.add(userName_text);
	    panel.add(password_label);
	    panel.add(password_text);
	    panel.add(conf_password_label);
	    panel.add(conf_password_text);
	    message = new JLabel();
	    panel.add(message);
	    panel.add(create);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    // Adding the listeners to components..
	    create.addActionListener(this);
	    add(panel, BorderLayout.CENTER);
	    setTitle("Create Account");
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
			temp.createAccount(userName, password);
		}

	}
}

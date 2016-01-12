package src.UI;

import java.awt.*;
import javax.swing.*;

import src.*;

import java.awt.event.*;

public class LoginUI {
	private final JLabel title = new JLabel("SYSU Bin", JLabel.CENTER);
	private final JLabel user = new JLabel("IP Address:");
	private final JLabel pswd = new JLabel("Port:");
	private final JTextField inputUsername = new JTextField(10);
	private final JTextField inputPassword = new JTextField(10);
	private final JButton btn1 = new JButton("Login");
	
	JFrame frame = new JFrame("Log In");
	
	private static UserInfo userInfo;
	
	public LoginUI(UserInfo UserInfo_) {
		final GridBagLayout gridbag = new GridBagLayout();
		frame.setLayout(gridbag);
		
		userInfo = UserInfo_;
		
		// Title Layout
		GridBagConstraints titleLayout = new GridBagConstraints();
		titleLayout.anchor = GridBagConstraints.CENTER;
		titleLayout.fill = GridBagConstraints.BOTH;
		titleLayout.insets = new Insets(0, 0, 0, 0);
		title.setBackground(new Color(200, 200, 200));
		titleLayout.weightx = 1;
		titleLayout.weighty = 1;
		
		// Title
		titleLayout.gridx = 0;
		titleLayout.gridy = 0;
		titleLayout.gridheight = 1;
		titleLayout.gridwidth = 0;
		title.setOpaque(true);
		title.setFont(new Font("Arial", Font.BOLD, 25));
		title.setForeground(new Color(100, 100, 100));
		frame.getContentPane().add(title, titleLayout);
		
		// Username&Password Layout
		GridBagConstraints inputLayout = new GridBagConstraints();
		inputLayout.anchor = GridBagConstraints.CENTER;
		inputLayout.fill = GridBagConstraints.BOTH;
		inputLayout.insets = new Insets(0, 30, 10, 30);
		inputLayout.weightx = 0;
		inputLayout.weighty = 0;
		
		// Add Username Label
		inputLayout.gridx = 1;
		inputLayout.gridy = 1;
		inputLayout.gridheight = 1;
		inputLayout.gridwidth = 2;
		frame.getContentPane().add(user, inputLayout);
		
		// Add Username TextField
		inputLayout.gridx = 3;
		inputLayout.gridy = 1;
		inputLayout.gridheight = 1;
		inputLayout.gridwidth = 3;
		inputUsername.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(inputUsername, inputLayout);
		
		// Add Password Label
		inputLayout.gridx = 1;
		inputLayout.gridy = 2;
		inputLayout.gridheight = 1;
		inputLayout.gridwidth = 2;
		frame.getContentPane().add(pswd, inputLayout);
		
		// Add Password TextField
		inputLayout.gridx = 3;
		inputLayout.gridy = 2;
		inputLayout.gridheight = 1;
		inputLayout.gridwidth = 3;
		inputUsername.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(inputPassword, inputLayout);
		
		// Button Field
		GridBagConstraints buttonLayout = new GridBagConstraints();
		buttonLayout.anchor = GridBagConstraints.CENTER;
		buttonLayout.fill = GridBagConstraints.BOTH;
		buttonLayout.insets = new Insets(0, 20, 20, 20);
		buttonLayout.weightx = 0;
		buttonLayout.weighty = 0;
		
		// Add Login button
		buttonLayout.gridx = 4;
		buttonLayout.gridy = 3;
		buttonLayout.gridheight = 1;
		buttonLayout.gridwidth = 2;
		btn1.setBorderPainted(false);
		btn1.setBackground(new Color(200, 200, 200));
		frame.getContentPane().add(btn1, buttonLayout);
	    /*
		// Add Login button
		buttonLayout.gridx = 5;
		buttonLayout.gridy = 3;
		buttonLayout.gridheight = 1;
		buttonLayout.gridwidth = 1;
		btn2.setBorderPainted(false);
		btn2.setBackground(new Color(200, 200, 200));
		frame.getContentPane().add(btn2, buttonLayout);
	    */
        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(222, 222, 222));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
		// Add Event to login
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
    			String _user = inputUsername.getText().trim();
    			String c_port = inputPassword.getText();
				
				if (_user == null || _user.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "Username cannot be null");
					return;
				}
				if (c_port == null || c_port.length() == 0) {
					JOptionPane.showMessageDialog(null, "Password cannot be null");
					return;
				} else {
					//int _port = Integer.parseInt(c_port);
					jumpToChatroom(_user, c_port);
				}
				 
			}
		});
	}
	
	@SuppressWarnings("static-access")
	private void jumpToChatroom(String _user, String _port) {
		frame.setVisible(false);
        userInfo.setFlag(false);
        userInfo.setIp(_user);
        userInfo.setPortNum(Integer.parseInt(_port));
	}
}

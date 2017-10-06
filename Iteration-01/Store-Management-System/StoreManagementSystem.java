import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class StoreManagementSystem {
	public static void main(String[] args) {
		DisplayController display = new DisplayController();
		display.displayLoginScreen();
	}
}

class DisplayController {
	int frameHeight = 600;
	int frameWidth = 600;		
	JButton exitButton;
	public DisplayController() {
		//There should always be an exit button somewhere on the application.
		//This constructor creates one ahead of time in the upper right corner.
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				exit();
			}
		});
		exitButton.setBounds(475, 10, 100, 50); //(x, y, xSize, ySize)
	}

	/**
	 *	Shows the screen with the button users can click to log in to the application.
	 */
	public void displayLoginScreen() {
		//Defines the frame used to display the information
		JFrame frame = new JFrame("Store Management System");
		frame.setSize(frameWidth, frameHeight);

		//Defines the login button.
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(200, 275, 200, 50);
		loginButton.setDefaultCapable(true); //If you press "enter" this button is automatically chosen.
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//frame.setVisible(false);
				enterCredentialsScreen();
			}
		});
		
		//Sets up the frame with both the login and exit buttons.
		frame.setLayout(null); //I really don't know why this is important, but otherwise the buttons are not sized properly.
		frame.add(loginButton);
		frame.add(exitButton);
		frame.setVisible(true);
	}

	public void enterCredentialsScreen() {
		JFrame frame = new JFrame("Credentials Screen");
		frame.setSize(frameWidth, frameHeight);

		//Creates text fields to enter username and password respectively. Also creates labels for these things.
		JTextField username = new JTextField("Username");
		username.setBounds(300, 100, 150, 50);
		JPasswordField password = new JPasswordField("Password");
		password.setBounds(300, 200, 150, 50);
		JLabel usernameLabel = new JLabel("Enter your username: ");
		usernameLabel.setBounds(150, 100, 150, 50);
		JLabel passwordLabel = new JLabel("Enter your password: ");
		passwordLabel.setBounds(150, 200, 150, 50);

		//The button to allow you to submit username and password.
		JButton submitCredentialsButton = new JButton("Submit");
		submitCredentialsButton.setBounds(200, 300, 200, 50);
		submitCredentialsButton.setDefaultCapable(true);
		submitCredentialsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (validateLogin("test", "test")) {
					//displayMainMenu();
					frame.setVisible(false);
					exit();
				}
				else {
					JOptionPane.showMessageDialog(frame, "Invalid username/password combination");
				}
			}
		});

		//Adds components to frame to be displayed.
		frame.setLayout(null); //Reset the frame.
		frame.add(exitButton);
		frame.add(username);
		frame.add(usernameLabel);
		frame.add(password);
		frame.add(passwordLabel);
		frame.add(submitCredentialsButton);
		frame.setVisible(true);
	}

	public boolean validateLogin(String username, String password) {
		if (username == "thatcher" && password == "rickertsen") {
			return true;
		}
		return false;
	}

	public void exit() {
		System.exit(0);
	}
}
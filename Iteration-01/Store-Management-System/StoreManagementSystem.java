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
	int frameHeight = 1000;
	int frameWidth = 1000;
	JButton exitButton;
	public DisplayController() {
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				exit();
			}
		});
		exitButton.setBounds(950, 100, 50, 900);
	}
	
	public void displayLoginScreen() {
		JButton loginButton = new JButton("Login");
		loginButton.setDefaultCapable(true);
		JFrame frame = new JFrame("Test");
		frame.setSize(frameHeight, frameWidth);
		frame.add(loginButton);
		frame.add(exitButton);
		frame.setVisible(true);
	}

	public void exit() {
		System.exit(0);
	}
}
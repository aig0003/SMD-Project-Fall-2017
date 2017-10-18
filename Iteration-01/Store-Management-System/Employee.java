import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Employee {
	int frameHeight = 600;
    int frameWidth = 600;		
    JButton exitButton;
	Login prevPage; //The page that created this one.
	public Employee(Login loginPage){
        //There should always be an exit button somewhere on the application.
        //This constructor creates one ahead of time in the upper right corner.
        prevPage = loginPage;
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                exit();
            }
        });
        exitButton.setBounds(475, 10, 100, 50); //(x, y, xSize, ySize)
	}
    public void DisplayEmployeeMenu() {
        //Defines the frame used to display the information
        JFrame frame = new JFrame("Employee Main Menu");
        frame.setSize(frameWidth, frameHeight);
		
        //Creates a button that allows the employee to check out a user.
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(200, 100, 200, 50);
        checkoutButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		frame.setVisible(false);
        		Checkout checkout = new Checkout();
        		checkout.mainCheckoutPage();
        	}
        });

		//Defines the "Order Management" button. 
		JButton orderButton = new JButton("Order Management");
        orderButton.setBounds(200, 200, 200, 50);//(x, y, xSize, ySize)
        orderButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //enterOrderScreen();
            }
        });

        //Defines the "Manage Inventory" button.
        JButton inventoryButton = new JButton("Manage Inventory");
        inventoryButton.setBounds(200, 275, 200, 50);//(x, y, xSize, ySize)
        inventoryButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //Shifts the control to the inventory page. This could cause memory leaks, but it would take a long time for them to be relevant.
                frame.setVisible(false);
                Inventory inventory = new Inventory();
                inventory.MainInventoryPage();
            }
        });
        
        //Adds a button allowing the user to log out and return to the login screen.
    	JButton logoutButton = new JButton("Logout");
    	logoutButton.setBounds(10, 10, 100, 50);
    	logoutButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			frame.setVisible(false);
				prevPage.displayLoginScreen();
			}
    	});
    	
        //Sets up the frame with both the inventory and exit buttons.
        frame.setLayout(null); //I really don't know why this is important, but otherwise the buttons are not sized properly.
		frame.add(orderButton);
		frame.add(inventoryButton);
        frame.add(logoutButton);
        frame.add(exitButton);
        frame.setVisible(true); 
    }

    public void exit()
    {
    	System.exit(0);
    }
}
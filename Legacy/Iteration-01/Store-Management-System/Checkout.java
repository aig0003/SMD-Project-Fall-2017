import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Checkout {
	int frameHeight = 600;
    int frameWidth = 600;		
    JButton exitButton;



	public Checkout()
	{
		exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                exit();
            }
        });
        exitButton.setBounds(475, 10, 100, 50); //(x, y, xSize, ySize)	
	}

	public void mainCheckoutPage() {
		JFrame frame = new JFrame("Checkout");
		frame.setSize(frameWidth, frameHeight);

		//User will click this when they have a new item to "scan"
		JButton addItemButton = new JButton("Scan Item")
		addItemButton.setBounds(10, 10, 100, 50);
		addItemButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
	  			String itemId =  JOptionPane.showInputDialog("Enter item's id");
	  			String query = "SELECT * FROM product WHERE " itemId " = id";
	  			//Submit the query to sql and get the appropriate data (name and price) back to display for user.

			}
		});

		JButton checkoutButton = new JButton("Checkout")
		checkoutButton.setBounds(475, 500, 100, 50);
		checkoutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//Creates an Order object containing the details of this order.
			}
		});

		frame.setLayout(null);
		frame.setVisible(true);
	}

	public void exit(){
		System.exit(0);
	}
	
}
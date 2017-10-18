import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Inventory {
	int frameHeight = 600;
	int frameWidth = 600;
	String[] productList = {"Magic Beans", "Dog Food", "Tortillas", "Bananas", "Fidget Spinners"};
	
	//This is sample data that should in the future be retrieved from sql.
	String[] itemData = {"100001", "Magic Beans", "13.37", "254", "<html>These are super expensive fancy beans. They don't taste very good</html>", "Beans Inc.", "1 bean", "1559347200"}; //This should be set by SQL. For now it is done manually.

	JButton exitButton;
	public Inventory(){
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

	public void MainInventoryPage(){
	  	JFrame frame = new JFrame("Inventory");
	  	frame.setSize(frameWidth, frameHeight);

	  	//Creates a button that allows the user to go back.
	  	JButton backButton = new JButton("Back");
	  	backButton.setBounds(10, 10, 100, 50);
	  	backButton.addActionListener(new ActionListener(){
	  		public void actionPerformed(ActionEvent e){
	  			frame.setVisible(false);
				Employee prevPage = new Employee(null);
				prevPage.DisplayEmployeeMenu();
	  		}
	  	});

	  	//Creates a button that allows the user to view inventory
	  	JButton viewInventoryButton = new JButton("View Inventory");
	  	viewInventoryButton.setBounds(100, 300, 350, 50);
	  	viewInventoryButton.addActionListener(new ActionListener(){
	  		public void actionPerformed(ActionEvent e){
	  			frame.setVisible(false);
	  			ViewInventoryPage();
	  		}
	  	});

	  	//Creates the "Search" text field.
	  	JTextField searchField = new JTextField("Search by item name...");
		searchField.setBounds(100, 200, 190, 50);

	  	//Creates the "search" button.
	  	JButton searchButton = new JButton("Search!");
	  	searchButton.addActionListener(new ActionListener(){
	  		public void actionPerformed(ActionEvent e){
	  			String searchResult = queryInventory("SELECT * FROM Product WHERE name = " + searchField.getText() + ";");
	  			if (searchResult == "" || searchResult == null) {
	  				JOptionPane.showMessageDialog(frame, "No results found");
	  			} else {
	  				frame.setVisible(false);
	  				UpdateInventoryPage(searchResult);
	  			}
	  		}
	  	});
		searchButton.setBounds(300, 200, 150, 50);

		JButton addItemButton = new JButton("Add Item");
		addItemButton.setBounds(100, 400, 350, 50);
		addItemButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(false);
				UpdateInventoryPage("NEW");
			}
		});

	  	frame.setLayout(null);
	  	frame.add(exitButton);
	  	frame.add(backButton);
	  	frame.add(searchButton);
	  	frame.add(viewInventoryButton);
	  	frame.add(addItemButton);
	  	frame.add(searchField);
	  	frame.setVisible(true);
	}

	//Displays all of the items in inventory
	public void ViewInventoryPage() {
		JFrame frame = new JFrame("Inventory");
		frame.setSize(frameWidth, frameHeight);

	  	//Creates a button that allows the user to go back.
	  	JButton backButton = new JButton("Back");
	  	backButton.setBounds(10, 10, 100, 50);
	  	backButton.addActionListener(new ActionListener(){
	  		public void actionPerformed(ActionEvent e){
	  			MainInventoryPage();	
  			}
	  	});

		JLabel instructions = new JLabel("Choose an item to change it or view details.");
		instructions.setBounds(150, 10, 400, 50);
		
		int currentOffset = 0; //Represents the amount that the button will be shifted down so items are not displayed on top of each other.
		for (String item: productList)
		{
			JButton newButton = new JButton(item);
			newButton.setBounds(50, 100 + currentOffset, 200, 50);
			currentOffset += 50;
			newButton.addActionListener(new ActionListener(){
		  		public void actionPerformed(ActionEvent e){
		  			frame.setVisible(false);
		  			UpdateInventoryPage(item);
		  		}
		  	});
			frame.add(newButton);
		}
		frame.setLayout(null);
		frame.setVisible(true);
		frame.add(exitButton);
		frame.add(instructions);
		frame.add(backButton);
	}

	//Shows the screen that allows the user to update the data about a particular item. The itemID parameter is the id of the item that will be updated.
	public void UpdateInventoryPage(String itemID){
		//Creates the frame for displayed info.
		JFrame frame = new JFrame("Inventory");
	  	frame.setSize(frameWidth, frameHeight);

	  	if (itemID == "NEW")
	  	{
	  		//The user is adding an item for the first time. First we should make a call to sql to create a new item. Then we retrieve the empty fields and proceed as normal.
	  	}

		for (int i = 0; i < itemData.length; i++)
		{
			int verticalOffset = i * 50; //This is the height that the items will be placed at.
			
			//Creates a button associated with each section that allows you to change the various values.
			JButton changeButton = new JButton("Change");
		  	changeButton.setBounds(50, 100 + verticalOffset, 100, 50);	
		  	changeButton.addActionListener(new ActionListener(){
		  		public void actionPerformed(ActionEvent e){
		  			String newValue =  JOptionPane.showInputDialog("Enter a new value");
		  			int dataNum = (changeButton.getBounds().y - 100) / 50; //I had trouble accessing a variable that would change later on and since this is based on the number, it just uses the y positioning to get the for loop iteration number.
		  			itemData[dataNum] = newValue;

		  			queryInventory("UPDATE storeItem SET " + getLabel(dataNum) + " = " + newValue + " WHERE id = " + itemID);

		  			frame.setVisible(false);
		  			UpdateInventoryPage(itemID); //Refreshes the page so the new data is displayed.
		  			//TODO: This would perform a SQL query to change the value to the new one.	 

		  		}
		  	});
		  	frame.add(changeButton);

		  	JLabel fieldLabel = new JLabel(getLabel(i) + ":");
		  	fieldLabel.setBounds(150, 100 + verticalOffset, 100, 50);
		  	frame.add(fieldLabel);

		  	//Adds the data for the field.
		  	JLabel fieldValue = new JLabel(itemData[i]);
	  		fieldValue.setBounds(250, 100 + verticalOffset, 350, 50);
	  		frame.add(fieldValue); 
		}

	  	//Creates a button allowing user to return back to the search menu.
	  	JButton backToMenuButton = new JButton("Back");
	  	backToMenuButton.addActionListener(new ActionListener(){
	  		public void actionPerformed(ActionEvent e){
	  			frame.setVisible(false);
	  			ViewInventoryPage();
	  		}
	  	});
	  	backToMenuButton.setBounds(25, 10, 200, 50);

	  	//Resets the frame so it displays correctly.
	  	frame.setLayout(null);
	  	//Adds buttons
	  	frame.add(exitButton);
	  	frame.add(backToMenuButton);
	  	//Sets frame to be visible to user
	  	frame.setVisible(true);
	}

	//Above we use number to reference the different sections. This is a simple switch statement that converts them to what they are.
	public String getLabel(int numIn) {
		switch (numIn) {
			case 0:
				return "ID";
			case 1:
				return "Name";
			case 2:
				return "Price";
			case 3:
				return "Quantity";
			case 4:
				return "Description";
			case 5:
				return "Supplier";
			case 6:
				return "Measurement";
			case 7:
				return "Expiration Date";
			default:
				System.out.println("Error!");
				exit();
		}
		return "ERROR";
	}

	public String queryInventory(String query){
		//Query SQL Here
	  	System.out.println(query);
	  	return query;
	}

	public void exit(){
		System.exit(0);
	}
}
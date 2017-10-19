import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Inventory {
	private int frameHeight = 600;
	private int frameWidth = 600;
	private QueryAdapter qa;

	private JFrame frame;

	JButton exitButton;
	public Inventory(QueryAdapter qaIn){
		qa = qaIn;
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

	  	frame = new JFrame("Inventory");
	  	frame.setSize(frameWidth, frameHeight);

	  	//Creates a button that allows the user to go back.
	  	JButton backButton = new JButton("Back");
	  	backButton.setBounds(10, 10, 100, 50);
	  	backButton.addActionListener(new ActionListener(){
	  		public void actionPerformed(ActionEvent e){
	  			frame.setVisible(false);
				StoreManagementSystem prevPage = new StoreManagementSystem();
				prevPage.displayStoreManagementMenu();
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
	  	final JTextField searchField = new JTextField("Search by item id...");
		searchField.setBounds(100, 200, 190, 50);

	  	//Creates the "search" button.
	  	JButton searchButton = new JButton("Search!");
	  	searchButton.addActionListener(new ActionListener(){
	  		public void actionPerformed(ActionEvent e){
	  			int idToSearch = Integer.parseInt((searchField.getText()));
	  			Product searchResult = qa.loadProduct(idToSearch);
	  			if (searchResult.getID() != idToSearch){ //Could not find the product.
					JOptionPane.showMessageDialog(frame, "No results found");
	  			} else {
	  				frame.setVisible(false);
	  				UpdateInventoryPage(idToSearch);
	  			}
	  		}
	  	});
		searchButton.setBounds(300, 200, 150, 50);

		JButton addItemButton = new JButton("Add Item");
		addItemButton.setBounds(100, 400, 350, 50);
		addItemButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(false);
				UpdateInventoryPage(-1);
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
		frame = new JFrame("Inventory");
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
		final Product[] productList = qa.getAll();

		for (int i = 0; i < productList.length; i++)
		{
			String itemName = productList[i].getName();
			JButton newButton = new JButton(itemName);
			final int currentOffset = i * 50;
			newButton.setBounds(50, 100 + currentOffset, 200, 50);
			newButton.addActionListener(new ActionListener(){
		  		public void actionPerformed(ActionEvent e){
		  			frame.setVisible(false);
		  			UpdateInventoryPage(productList[currentOffset / 50].getID());
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
	public void UpdateInventoryPage(final int itemID){
		//Creates the frame for displayed info.
		frame = new JFrame("Update/Add Item");
	  	frame.setSize(frameWidth, frameHeight);

	  	final int numFields = 8; //There are 8 distinct data fields for each product.

	  	QueryAdapter qa = Application.getInstance().getQuery();
	  	final Product product = qa.loadProduct(itemID);

	  	if (itemID == -1) {
			//This only happens when you are adding a new item. To do this, sql will create an empty column and then proceed as normal.
	  	}

		for (int i = 0; i < numFields; i++)
		{
			int verticalOffset = i * 50; //This is the height that the items will be placed at.
			
			//Creates a button associated with each section that allows you to change the various values.
			final JButton changeButton = new JButton("Change");
			final QueryAdapter innerQA = qa; //This is just a copy of the query adapter that each button has so that it doesnt complain about it being within an inner class.
		  	changeButton.setBounds(50, 100 + verticalOffset, 100, 50);	
		  	changeButton.addActionListener(new ActionListener(){
		  		public void actionPerformed(ActionEvent e){
		  			String newValue =  JOptionPane.showInputDialog("Enter a new value");
		  			int dataNum = (changeButton.getBounds().y - 100) / 50; //I had trouble accessing a variable that would change later on and since this is based on the number, it just uses the y positioning to get the for loop iteration number.
					setData(dataNum, product, newValue);
		  			innerQA.saveProduct(product);
					frame.setVisible(false);
		  			UpdateInventoryPage(itemID); //Refreshes the page so the new data is displayed.
		  		}
		  	});
		  	frame.add(changeButton);

		  	JLabel fieldLabel = new JLabel(getLabel(i) + ":");
		  	fieldLabel.setBounds(150, 100 + verticalOffset, 100, 50);
		  	frame.add(fieldLabel);

		  	//Adds the data for the field.
		  	JLabel fieldValue = new JLabel(getData(i, product));
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
	private String getLabel(int numIn) {
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

	//Gets the data for a product related to the given numbered data field.
	private String getData(int dataNum, Product product) {
		switch(dataNum) {
			case 0:
				return Integer.toString(product.getID());
			case 1:
				return product.getName();
			case 2:
				return Double.toString(product.getPrice());
			case 3:
				return Double.toString(product.getQuantity());
			case 4:
				return product.getDescription();
			case 5:
				return product.getSupplier();
			case 6:
				return product.getUnit();
			case 7:
				return product.getExpiration().toString();
			default:
				System.out.println("Error!");
				exit();
		}
		return "ERROR";
	}

	//Sets the data for a product related to the given numbered data field.
	private Product setData(int dataNum, Product product, String newValue) {
		try {
			switch (dataNum) {
				case 0:
					product.setID(Integer.parseInt(newValue));
					break;
				case 1:
					product.setName(newValue);
					break;
				case 2:
					product.setPrice(Double.parseDouble(newValue));
					break;
				case 3:
					product.setQuantity(Double.parseDouble(newValue));
					break;
				case 4:
					product.setDescription(newValue);
					break;
				case 5:
					product.setSupplier(newValue);
					break;
				case 6:
					product.setUnit(newValue);
					break;
				case 7:
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					try { //I really don't know why we need this try/catch block but it makes intellij happy.
						product.setExpiration((Date)formatter.parse(newValue));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					break;
				default:
					System.out.println("An unknown error occurred");
			}
		} catch (Error e) {
			JOptionPane.showMessageDialog(frame, "Error! Most likely the data you entered is incorrect. Check terminal for details.");
			System.out.println(e);
		}
		return product;
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
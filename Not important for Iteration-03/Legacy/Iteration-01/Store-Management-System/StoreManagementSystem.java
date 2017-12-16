import javax.swing.*;
import java.awt.event.*;
import java.util.*;

//This class only contains the main method for the program that initializes it. This first starts out on the login page.
class StoreManagementSystem {
	public static void main(String[] args) {
		//This is how the application will start in the release build.
		Login login = new Login();
		login.displayLoginScreen();

		/**Allows you to test the inventory pages.
		Inventory inventory = new Inventory();
		inventory.SearchInventoryPage();*/
	}
}


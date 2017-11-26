import javax.swing.*;
import java.awt.*;

public class StoreManagementSystemView extends JFrame {
    // String Labels/Constants
    private static int PW = 600; private static int PH = 240; // Page width & height dimension
    private static int BW = 100; private static int BH = 25; // Button width & height dimensions
    private static String pageTitle = "Store Management System";

    //Buttons
    private JButton logoutButton = new JButton("Logout");
    private JButton checkoutButton = new JButton("Checkout");
    private JButton profileButton = new JButton("Profile");
    private JButton inventoryButton = new JButton("Inventory");
    private JButton usersButton = new JButton("Users");

    public StoreManagementSystemView() {
        Employee currentUser = Application.getCurrentUser();

        if (currentUser != null) { //Only does the actual work if a user is logged in. Otherwise it creates a placeholder page.
            this.setTitle(pageTitle);
            this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(PW, PH);

            //Set size of buttons
            checkoutButton.setPreferredSize(new Dimension(BW, BH)); //(buttonWidth, buttonHeight)
            profileButton.setPreferredSize(new Dimension(BW, BH));
            inventoryButton.setPreferredSize(new Dimension(BW, BH));
            usersButton.setPreferredSize(new Dimension(BW, BH));
            logoutButton.setPreferredSize(new Dimension(BW, BH));

            // Title label panel
            JLabel title = new JLabel(pageTitle);
            title.setFont(new Font("Sans Serif", Font.BOLD, 24));
            JPanel panelTitle = new JPanel();
            panelTitle.add(title);
            this.getContentPane().add(panelTitle);

            // Buttons panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(logoutButton);
            int employeeType = currentUser.getEmployeeType();
            if (employeeType == 1) { // If employee is a manager type
                buttonPanel.add(usersButton);
            }
            buttonPanel.add(checkoutButton);
            buttonPanel.add(profileButton);
            buttonPanel.add(inventoryButton);

            this.getContentPane().add(buttonPanel);
        }
    }

    public JButton getLogoutButton() { return logoutButton; }
    public JButton getCheckoutButton() { return checkoutButton; }
    public JButton getProfileButton() { return profileButton; }
    public JButton getInventoryButton() { return inventoryButton; }
    public JButton getUsersButton() { return usersButton; }
}

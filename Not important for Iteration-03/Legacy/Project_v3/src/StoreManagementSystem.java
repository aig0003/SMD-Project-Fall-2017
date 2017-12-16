import javax.swing.*;
import java.awt.event.*;

public class StoreManagementSystem {

    private final int frameHeight = 600;
    private final int frameWidth = 600;

    private JButton exitButton = new JButton("Exit");

    public StoreManagementSystem() {
        //Creates an exit button to make it look standard with the rest of the application.
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        exitButton.setBounds(475, 10, 75, 30);
    }

    public void displayStoreManagementMenu(){
        final JFrame frame = new JFrame("Main Menu");
        frame.setResizable(false);
        frame.setSize(frameWidth, frameHeight);
        frame.setLayout(null);

        JLabel title = new JLabel("<html><center>Store Management System</center></html>");
        title.setBounds(225, 10, 200, 50);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(225, 200, 150, 50);
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getCheckoutScreen().setVisible(true);
                frame.setVisible(false);
            }
        });
        JButton manageInventoryButton = new JButton("Manage Inventory");
        manageInventoryButton.setBounds(225, 300, 150, 50);
        manageInventoryButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Application.getInstance().getInventoryScreen().MainInventoryPage();
                frame.setVisible(false);
            }
        });
        frame.add(title);
        frame.add(checkoutButton);
        frame.add(exitButton);
        frame.add(manageInventoryButton);
        frame.setVisible(true);
    }
}

//TODO: Format buttons
import javax.swing.*;
import java.awt.*;

public class InventoryScreenView extends JFrame {
    // String Labels/Constants
    private static int PW = 600; private static int PH = 240; // Page width & height dimension
    private static int BW = 180; private static int BH = 25; // Button width & height dimensions
    private static String pageTitle = "Inventory Management";

    private JButton inventoryButton = new JButton("Manage Inventory");
    private JButton reportButton = new JButton("Business Report");
    private JButton backButton = new JButton("Back");

    public InventoryScreenView() {
        this.setTitle(pageTitle);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(PW, PH);

        //Set size of buttons
        reportButton.setPreferredSize(new Dimension(BW, BH));
        inventoryButton.setPreferredSize(new Dimension(BW, BH));
        backButton.setPreferredSize(new Dimension(BW, BH));

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(inventoryButton);
        buttonPanel.add(reportButton);

        // Back button panel
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);

        this.getContentPane().add(buttonPanel);
        this.getContentPane().add(backButtonPanel);
    }
    public JButton getInventoryButton() { return inventoryButton; }
    public JButton getReportButton() { return reportButton; }
    public JButton getBackButton() { return backButton; }
}

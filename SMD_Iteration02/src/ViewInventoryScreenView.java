import javax.swing.*;
import java.awt.*;

public class ViewInventoryScreenView extends JFrame{

    // String Labels/Constants
    private static int PW = 600; private static int PH = 240; // Page width & height dimension
    private static int LW = 200; private static int LH = 25; // Dimensions for labels on this view.
    private static int BW = 180; private static int BH = 25; // Button width & height dimensions
    private static String pageTitle = "Inventory Management";

    private JButton backButton = new JButton("Back");
    private JButton addProductButton = new JButton("Add/Search Product");

    public ViewInventoryScreenView() {
        this.setTitle(pageTitle);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(PW, PH);

        //Set size of buttons
        backButton.setPreferredSize(new Dimension(BW, BH));
        addProductButton.setPreferredSize(new Dimension(BW, BH));

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(addProductButton);

        this.getContentPane().add(buttonPanel);

        //Buttons for individual items are added procedurally in the controller.
    }

    public JButton getBackButton() { return backButton; }
    public JButton getAddProductButton() { return addProductButton; }

    public int[] getButtonDimensions() {
            return new int[] {BW, BH};
        }
}

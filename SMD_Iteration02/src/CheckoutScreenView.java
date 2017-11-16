//TODO: Implement 'Finish and Pay' button (Needs to reduce stock available) & print receipt
//TODO: Needs to link to find a way to store checkout data and put in a displayable format for Business Report
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CheckoutScreenView extends JFrame{
    // String labels/Dimension Constants
    private static int PW = 600; private static int PH = 480; // Page width & height dimension
    private static int BW = 120; private static int BH = 50; // Button width & height dimensions
    private static String PAGETITLE = "Checkout";

    //Buttons
    private JButton addButton = new JButton("Add new item");
    private JButton payButton = new JButton("Finish and Pay");
    private JButton backButton = new JButton("Back");
    //Table
    private DefaultTableModel items = new DefaultTableModel();
    private JTable table = new JTable(items); // null, new String[]{"ProductID", "Product Name", "Price", "Quantity", "Cost"});
    //Labels
    private JLabel totalCost = new JLabel("Total: ");

    public CheckoutScreenView() {
        this.setTitle(PAGETITLE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(PW, PH);

        //Table Columns
        items.addColumn("Product ID");
        items.addColumn("Name");
        items.addColumn("Price");
        items.addColumn("Quantity");
        items.addColumn("Cost");

        // Table panel
        JPanel orderPanel = new JPanel();
        orderPanel.setPreferredSize(new Dimension(400, 450));
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.PAGE_AXIS));
        table.setBounds(0, 0, 400, 350);
        orderPanel.add(table.getTableHeader());
        orderPanel.add(table);
        orderPanel.add(totalCost);
        table.setFillsViewportHeight(true);
        this.getContentPane().add(orderPanel);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 100));
        buttonPanel.add(backButton);
        buttonPanel.add(addButton);
        buttonPanel.add(payButton);
        this.getContentPane().add(buttonPanel);
    }

    public void addRow(Object[] row) {
        items.addRow(row);
        items.fireTableDataChanged();
    }

    // Frame element getters
    public JButton getAddButton() {
        return addButton;
    }
    public JButton getPayButton() {
        return payButton;
    }
    public JButton getBackButton() { return backButton; }
    public JLabel getLabTotal() { return totalCost; }

}

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
    private JButton addButton = new JButton("Add item");
    private JButton removeButton = new JButton("Remove Previous");
    private JButton payButton = new JButton("Finish and Pay");
    private JButton backButton = new JButton("Back");
    //Table
    private DefaultTableModel items = new DefaultTableModel();
    private JTable table = new JTable(items);
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
        buttonPanel.add(removeButton);
        buttonPanel.add(addButton);
        buttonPanel.add(payButton);
        this.getContentPane().add(buttonPanel);
    }
    private int entries = 0;
    public void addRow(Object[] row) {
        items.addRow(row);
        items.fireTableDataChanged();
        entries++;
    }

    public void removeRow() {
        items.removeRow(entries-1);
        items.fireTableDataChanged();
        entries--;
    }

    public String[] getRowAt(int row) {
        int colNumber = 5;
        String[] result = new String[colNumber];
        result[0] = String.valueOf(table.getValueAt(row,0));
        result[1] = String.valueOf(table.getValueAt(row,1));
        result[2] = String.valueOf(table.getValueAt(row,2));
        result[3] = String.valueOf(table.getValueAt(row,3));
        result[4] = String.valueOf(table.getValueAt(row,4));
        return result;
    }

    // Frame element getters
    public JButton getAddButton() {
        return addButton;
    }
    public JButton getRemoveButton() { return removeButton; }
    public JButton getPayButton() {
        return payButton;
    }
    public JButton getBackButton() { return backButton; }
    public JLabel getLabTotal() { return totalCost; }
    public JTable getTable() { return table; }
    public int getEntries() {return entries;}

}

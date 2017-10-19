
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutScreen extends JFrame {

    private JButton addBtn = new JButton("Add a new item");
    private JButton payBtn = new JButton("Finish and Pay");
    private JButton backButton = new JButton("Back");
    private JButton exitButton = new JButton("Exit");

    private DefaultTableModel items = new DefaultTableModel();

    private JTable table = new JTable(items); // null, new String[]{"ProductID", "Product Name", "Price", "Quantity", "Cost"});
    private JLabel totalCost = new JLabel("Total: ");

    public CheckoutScreen() {
        exitButton.setPreferredSize(new Dimension(75, 30));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        backButton.setPreferredSize(new Dimension(75,30));
        CheckoutScreen thisReference = this;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                thisReference.setVisible(false);
                Application.getInstance().getSMSSceen().displayStoreManagementMenu();
            }
        });
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel navPanel = new JPanel();
        navPanel.setPreferredSize(new Dimension(100, 50));

        navPanel.add(backButton);
        navPanel.add(exitButton);
        this.getContentPane().add(navPanel);

        this.setTitle("Checkout");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(600, 600);


        items.addColumn("Product ID");
        items.addColumn("Name");
        items.addColumn("Price");
        items.addColumn("Quantity");
        items.addColumn("Cost");

        JPanel panelOrder = new JPanel();
        panelOrder.setPreferredSize(new Dimension(400, 450));
        panelOrder.setLayout(new BoxLayout(panelOrder, BoxLayout.PAGE_AXIS));
        table.setBounds(0, 0, 400, 350);
        panelOrder.add(table.getTableHeader());
        panelOrder.add(table);
        panelOrder.add(totalCost);
        table.setFillsViewportHeight(true);
        this.getContentPane().add(panelOrder);


        JPanel panelButton = new JPanel();
        panelButton.setPreferredSize(new Dimension(400, 100));
        panelButton.add(addBtn);
        panelButton.add(payBtn);
        this.getContentPane().add(panelButton);

    }

    public JButton getAddBtn() {
        return addBtn;
    }
    public JButton getPayBtn() {
        return payBtn;
    }

    public JLabel getLabTotal() {
        return totalCost;
    }

    public void addRow(Object[] row) {
        items.addRow(row);
        items.fireTableDataChanged();
    }
}

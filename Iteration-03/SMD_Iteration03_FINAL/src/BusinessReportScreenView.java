import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class BusinessReportScreenView extends  JFrame {

    private SQLDataAdapter adapter;

    // String labels/Dimension Constants
    private static int PW = 600; private static int PH = 240; // Page width & height dimension
    private static int BW = 100; private static int BH = 25; // Button width & height dimensions
    private static String pageTitle = "Business Report";

    //Buttons
    private JButton printReportButton = new JButton("Print");
    private JButton backButton = new JButton("Back");

    //Tables, override required for proper sorting
    private DefaultTableModel items = new DefaultTableModel() {
        @Override
        public Class getColumnClass(int columnIndex) {
            return Integer.class;
        }
    };
    private JTable table = new JTable(items);

    public BusinessReportScreenView(SQLDataAdapter adapter) {
        this.adapter = adapter;
        this.setTitle(pageTitle);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(PW, PH);

        backButton.setPreferredSize(new Dimension(BW, BH));
        printReportButton.setPreferredSize(new Dimension(BW, BH)); //(buttonWidth, buttonHeight)

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(printReportButton);

        // Table Columns
        items.addColumn("Order ID");
        items.addColumn("Product ID");
        items.addColumn("Units Sold");
        items.addColumn("Revenue");

        //Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(400,400));
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));
        table.setBounds(0, 0, 400, 350);

        table.setAutoCreateRowSorter(true);        //Enables row sorting

        tablePanel.add(table.getTableHeader());
        tablePanel.add(table);
        table.setFillsViewportHeight(true);
        JScrollPane scrollableTablePanel = new JScrollPane(tablePanel);

        this.getContentPane().add(buttonPanel);
        this.getContentPane().add(scrollableTablePanel, BorderLayout.CENTER);
    }

    public void loadRows() {
        //Add data to table
        int entries = adapter.getEntries();
        for(int i = 0; i <= entries; i++) {
            items.addRow(adapter.loadOrderLine(i));
            double doub;
            try {
                doub = Double.parseDouble(items.getValueAt(i,3).toString());
            } catch (NullPointerException e) {
                doub = 0;
            }
            items.setValueAt(doub,i,3);
        }
        this.invalidate();
    }

    public void addRow(Object[] row) {
        items.addRow(row);
        items.fireTableDataChanged();
    }

    public JButton getBackButton() { return backButton; }
    public JButton getPrintReportButton() { return printReportButton; }
    public JTable getTable() { return table; }
}


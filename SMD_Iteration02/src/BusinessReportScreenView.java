import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

//TODO: Needs to link to find a way to store checkout data and put in a displayable format
public class BusinessReportScreenView extends  JFrame {
    // String labels/Dimension Constants
    private static int PW = 600; private static int PH = 240; // Page width & height dimension
    private static int BW = 100; private static int BH = 25; // Button width & height dimensions
    private static String pageTitle = "Business Report";

    //Buttons
    private JButton printReportButton = new JButton("Print");
    private JButton backButton = new JButton("Back");

    //Tables
    private DefaultTableModel items = new DefaultTableModel();
    private JTable table = new JTable(items);
    //Labels

    public BusinessReportScreenView() {
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
        items.addColumn("Test");

        //Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(400,400));
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));
        table.setBounds(0, 0, 400, 350);
        tablePanel.add(table.getTableHeader());
        tablePanel.add(table);
        table.setFillsViewportHeight(true);

        JScrollPane scrollableTablePanel = new JScrollPane(tablePanel);

        this.getContentPane().add(buttonPanel);
        this.getContentPane().add(scrollableTablePanel, BorderLayout.CENTER);
    }

    public JButton getBackButton() { return backButton; }
    public JButton getPrintReportButton() { return printReportButton; }
    public JTable getTable() { return table; }
}


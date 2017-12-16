import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

public class BusinessReportScreenController implements ActionListener {
    private BusinessReportScreenView view;
    private SQLDataAdapter adapter;

    public BusinessReportScreenController(BusinessReportScreenView view, SQLDataAdapter adapter) {
        this.view = view;
        this.adapter = adapter;
        view.loadRows();
        view.getPrintReportButton().addActionListener(this);
        view.getBackButton().addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        JTable table = view.getTable();
        if (e.getSource() == view.getPrintReportButton()) {
            printReport(table);
            return;
        } else if(e.getSource() == view.getBackButton()) {
            Application.getInstance().getBusinessReportScreenView().setVisible(false);
            Application.getInstance().getInventoryScreenView().setVisible(true);
            return;
        }

    }
    public void printReport(JTable table) {
        System.out.println("MESSAGE: Printing Report...");
        try {
            boolean complete = table.print();
            if (complete) {
                System.out.println("MESSAGE: Printing Successful");
            } else {
                System.out.println("MESSAGE: Printing cancelled");
            }
        } catch (PrinterException pe) {
            System.out.println("ERROR: Printing failed");
        }
    }
}

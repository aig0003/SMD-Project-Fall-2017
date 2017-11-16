//TODO:Need to link inventory button
//TODO:Remember to remove dialogue boxes when done

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryScreenController implements ActionListener{
    private InventoryScreenView view;
    private SQLDataAdapter adapter;

    public InventoryScreenController(InventoryScreenView view, SQLDataAdapter adapter) {
        this.view = view;
        this.adapter = adapter;

        view.getInventoryButton().addActionListener(this);
        view.getProductsButton().addActionListener(this);
        view.getReportButton().addActionListener(this);
        view.getBackButton().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getInventoryButton()) {
            JOptionPane.showMessageDialog(null, "Inventory Selected");
            return;
        } else if (e.getSource() == view.getProductsButton()) {
            Application.getInstance().getProductScreenView().setVisible(true);
            return;
        } else if (e.getSource() == view.getReportButton()) {
            Application.getInstance().getBusinessReportScreenView().setVisible(true);
            Application.getInstance().getInventoryScreenView().setVisible(false);
            return;
        } else if (e.getSource() == view.getBackButton()) {
            Application.getInstance().getInventoryScreenView().setVisible(false);
            Application.getInstance().getStoreManagementSystemView().setVisible(true);
            return;
        }

    }
}

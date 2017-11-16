import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagementSystemController implements ActionListener {
    private StoreManagementSystemView view;
    private SQLDataAdapter adapter;

    public StoreManagementSystemController(StoreManagementSystemView view, SQLDataAdapter adapter){
        this.view = view;
        this.adapter = adapter;

        view.getCheckoutButton().addActionListener(this);
        view.getProfileButton().addActionListener(this);
        view.getInventoryButton().addActionListener(this);
        view.getUsersButton().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getCheckoutButton()) {
            Application.getInstance().getCheckoutScreenView().setVisible(true);
            Application.getInstance().getStoreManagementSystemView().setVisible(false);
            return;
        } else if (e.getSource() == view.getProfileButton()) {
            Application.getInstance().getProfileScreenView().setVisible(true);
            Application.getInstance().getStoreManagementSystemView().setVisible(false);
            return;
        } else if (e.getSource() == view.getInventoryButton()) {
            Application.getInstance().getInventoryScreenView().setVisible(true);
            Application.getInstance().getStoreManagementSystemView().setVisible(false);
            return;
        } else if (e.getSource() == view.getUsersButton()) {
            Application.getInstance().getUsersScreenView().setVisible(true);
            Application.getInstance().getStoreManagementSystemView().setVisible(false);
            return;
        } else if (e.getSource() == view.getLogoutButton()) {
            JOptionPane.showMessageDialog(null,"Login features need to be implemented");
        }
    }
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryScreenController implements ActionListener{
    private InventoryScreenView view;
    private SQLDataAdapter adapter;

    public InventoryScreenController(InventoryScreenView view, SQLDataAdapter adapter) {
        this.view = view;
        this.adapter = adapter;

        view.getInventoryButton().addActionListener(this);
        view.getReportButton().addActionListener(this);
        view.getBackButton().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getInventoryButton()) {
            Application.getInstance().getViewInventoryScreenView().setVisible(true);
            Application.getInstance().getInventoryScreenView().setVisible(false);
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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutScreenController implements ActionListener {
    private CheckoutScreenView view;
    private SQLDataAdapter adapter; // to save and load product
    private Order order = null;

    public CheckoutScreenController(CheckoutScreenView view, SQLDataAdapter adapter) {
        this.adapter = adapter;
        this.view = view;

        view.getAddButton().addActionListener(this);
        view.getPayButton().addActionListener(this);
        view.getBackButton().addActionListener(this);

        order = new Order();
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == view.getAddButton()){ addProduct();}
        else if (e.getSource() == view.getPayButton()){ saveOrder();}
        else if (e.getSource() == view.getBackButton()) { back(); }
    }
    private void saveOrder() {
        JOptionPane.showMessageDialog(null, "This function is being implemented.");
    }

    private void back() {
        Application.getInstance().getStoreManagementSystemView().setVisible(true);
        Application.getInstance().getCheckoutScreenView().setVisible(false);
    }

    private void addProduct() {
        String id = JOptionPane.showInputDialog("Product ID#: ");
        Product product = adapter.loadProduct(Integer.parseInt(id));
        if (product == null) {
            JOptionPane.showMessageDialog(null, "ERROR: Product does not exist.");
            return;
        }

        double quantity = Double.parseDouble(JOptionPane.showInputDialog(null,"Quantity: "));

        if (quantity < 0 || quantity > product.getQuantity()) {
            JOptionPane.showMessageDialog(null, "ERROR: quantity is not valid!");
            return;
        }

        OrderLine line = new OrderLine();
        line.setOrderID(this.order.getOrderID());
        line.setProductID(product.getID());
        line.setQuantity(quantity);
        line.setCost(quantity * product.getPrice());

        //Updates new quantity, and stores it back into Product
        product.setQuantity(product.getQuantity() - quantity);
        adapter.saveProduct(product);

        order.getCheckoutLine().add(line);
        order.setTotalCost(order.getTotalCost() + line.getCost());

        Object[] row = new Object[5];
        row[0] = line.getID();
        row[1] = product.getName();
        row[2] = product.getPrice();
        row[3] = line.getQuantity();
        row[4] = line.getCost();

        this.view.addRow(row);
        this.view.getLabTotal().setText("Total: " + order.getTotalCost());
        this.view.invalidate();
    }
}

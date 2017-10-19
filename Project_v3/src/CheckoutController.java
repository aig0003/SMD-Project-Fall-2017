import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutController implements ActionListener {
    private CheckoutScreen cs;
    private QueryAdapter qa; // to save and load product
    private Order order = null;

    public CheckoutController(CheckoutScreen cs, QueryAdapter qa) {
        this.qa = qa;
        this.cs = cs;

        cs.getAddBtn().addActionListener(this);
        cs.getPayBtn().addActionListener(this);

        order = new Order();
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == cs.getAddBtn()){ addProduct();}
        else if (e.getSource() == cs.getPayBtn()){ saveOrder();}
    }

    private void saveOrder() {
        JOptionPane.showMessageDialog(null, "This function is being implemented.");
    }

    private void addProduct() {
        String id = JOptionPane.showInputDialog("Product ID#: ");
        Product product = qa.loadProduct(Integer.parseInt(id));
        if (product == null) {
            JOptionPane.showMessageDialog(null, "ERROR: Product does not exist.");
            return;
        }

        double quantity = Double.parseDouble(JOptionPane.showInputDialog(null,"Quantity: "));

        if (quantity < 0 || quantity > product.getQuantity()) {
            JOptionPane.showMessageDialog(null, "ERROR: quantity is not valid!");
            return;
        }

        OrderCheckout line = new OrderCheckout();
        line.setOrderID(this.order.getOrderID());
        line.setProductID(product.getID());
        line.setQuantity(quantity);
        line.setCost(quantity * product.getPrice());

        //Updates new quantity, and stores it back into Product
        product.setQuantity(product.getQuantity() - quantity);
        qa.updateProduct(product, product.getID());
        order.getCheckoutOrder().add(line);
        order.setTotalCost(order.getTotalCost() + line.getCost());

        Object[] row = new Object[5];
        row[0] = line.getID();
        row[1] = product.getName();
        row[2] = product.getPrice();
        row[3] = line.getQuantity();
        row[4] = line.getCost();

        this.cs.addRow(row);
        this.cs.getLabTotal().setText("Total: " + order.getTotalCost());
        this.cs.invalidate();
    }

}
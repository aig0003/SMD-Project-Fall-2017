import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;

public class CheckoutScreenController implements ActionListener {
    private CheckoutScreenView view;
    private SQLDataAdapter adapter; // to save and load product
    private Order order = null;
    private Product product = null;
    private double quantity = 0;
    private DecimalFormat format = new DecimalFormat("0.00");


    public CheckoutScreenController(CheckoutScreenView view, SQLDataAdapter adapter) {
        this.adapter = adapter;
        this.view = view;

        view.getAddButton().addActionListener(this);
        view.getRemoveButton().addActionListener(this);
        view.getPayButton().addActionListener(this);
        view.getBackButton().addActionListener(this);

        order = new Order();
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == view.getAddButton()){ addOrderEntry();}
        else if (e.getSource() == view.getPayButton()){ saveOrder(); printTable(view.getTable());}
        else if (e.getSource() == view.getRemoveButton()) {if(view.getEntries() > 0)removeOrderEntry();}
        else if (e.getSource() == view.getBackButton()) { back(); }
    }

    private void back() {
        Application.getInstance().getStoreManagementSystemView().setVisible(true);
        Application.getInstance().getCheckoutScreenView().setVisible(false);
    }

    private void addOrderEntry() {
        String id = JOptionPane.showInputDialog("Product ID#: ");
        try {
            product = adapter.loadProduct(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ERROR: Incorrect format.");
            return;
        }
        if (product == null) { JOptionPane.showMessageDialog(null, "ERROR: Product does not exist.");return; }

        try {
            quantity = Double.parseDouble(JOptionPane.showInputDialog(null,"Quantity: "));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ERROR: Incorrect format.");
            return;
        }

        if (quantity < 0 || quantity > product.getQuantity()) {
            JOptionPane.showMessageDialog(null, "ERROR: quantity is not valid!");
            return;
        }

        OrderLine line = new OrderLine();
        line.setOrderID(this.order.getOrderID());
        line.setProductID(product.getID());
        line.setQuantity(quantity);
        line.setCost(quantity * product.getPrice());
        product.setQuantity(product.getQuantity() - quantity); //Updates new quantity, and stores it back into Product
        adapter.saveProduct(product);

        order.getCheckoutLine().add(line);
        order.setTotalCost(order.getTotalCost() + line.getCost());

        Object[] row = new Object[5];
        row[0] = line.getProductID();
        row[1] = product.getName();
        row[2] = product.getPrice();
        row[3] = line.getQuantity();
        row[4] = format.format(line.getCost());

        this.view.addRow(row);
        this.view.getLabTotal().setText("Total: $" + format.format(order.getTotalCost()));
        this.view.invalidate();
    }

    public void removeOrderEntry() {
        OrderLine line = new OrderLine();
        line.setQuantity(quantity);
        line.setCost(quantity * product.getPrice());
        order.setTotalCost(order.getTotalCost() - line.getCost());
        product.setQuantity(product.getQuantity() + quantity); // Setting quantity back to previous amount
        adapter.saveProduct(product);
        this.view.getLabTotal().setText("Total: $" + format.format(order.getTotalCost()));
        this.view.invalidate();
        this.view.removeRow();
    }

    private void saveOrder() {
        int entries = view.getEntries();
        int orderID = adapter.getEntries() + 1;

        for(int i = 0; i < entries; i++) {
            OrderLine orderLine = new OrderLine();
            orderLine.setOrderID(orderID);

            int productID = Integer.parseInt(view.getRowAt(i)[0]);
            orderLine.setProductID(productID);

            double quantity = Double.parseDouble(view.getRowAt(i)[3]);
            orderLine.setQuantity(quantity);

            double cost = Double.parseDouble(view.getRowAt(i)[4]);
            orderLine.setCost(cost);

            adapter.saveOrder(orderLine);
        }
    }

    public void printTable(JTable table) {
        int entries = view.getEntries();
        System.out.println("MESSAGE: Printing Receipt...");
        try {
            boolean complete = table.print();
            if (complete) {
                System.out.println("MESSAGE: Printing Successful");
                for(int i = 0; i < entries; i++) {
                    view.removeRow();
                }
            } else {
                System.out.println("MESSAGE: Printing cancelled");
            }
        } catch (PrinterException pe) {
            System.out.println("ERROR: Printing failed");
        }
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutController implements ActionListener {
    private CheckoutScreen cs;
    private QueryAdapter qa;

    public CheckoutController(CheckoutScreen cs, QueryAdapter qa){
        this.cs = cs;
        this.qa = qa;

        cs.getAddBtn().addActionListener(this);
        cs.getCheckoutBtn().addActionListener( this);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == cs.getAddBtn())
            addProductToCheckout();
    }

    private void addProductToCheckout() {
        int ID = 0;
        try {
            ID = Integer.parseInt(cs.getTxtProductID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product ID.!");
            return;
        }
        Product product = qa.loadProduct(ID);
        if (product == null) {
            JOptionPane.showMessageDialog(null, "Product does not exist.");
            return;
        }

        cs.getTxtProductName().setText(product.getName());
        cs.getTxtProductPrice().setText(String.valueOf(product.getPrice()));
    }


}

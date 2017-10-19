import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductController implements ActionListener {
    private ProductScreen ps;
    private QueryAdapter qa;

    public ProductController(ProductScreen ps, QueryAdapter qa){
        this.qa = qa;
        this.ps = ps;

        ps.getLoadBtn().addActionListener(this);
        ps.getSaveBtn().addActionListener( this);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == ps.getLoadBtn())
            loadProduct();
        else
        if (e.getSource() == ps.getSaveBtn())
            saveProduct();
    }

    private void saveProduct() {
        int ID;
        try {
            ID = Integer.parseInt(ps.getTxtProductID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product ID.");
            return;
        }

        double productPrice;
        try {
            productPrice = Double.parseDouble(ps.getTxtProductPrice().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product price! Please provide a valid product price!");
            return;
        }

        double productQuantity;
        try {
            productQuantity = Double.parseDouble(ps.getTxtProductQuantity().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product quantity.");
            return;
        }

        String productName = ps.getTxtProductName().getText().trim();
        if (productName.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid product name.");
            return;
        }

        String productDescription = ps.getTxtProductDescription().getText().trim();
        if (productDescription.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid product description.");
            return;
        }

        String productSupplier = ps.getTxtProductSupplier().getText().trim();
        if (productSupplier.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid product supplier.");
            return;
        }

        String productUnit = ps.getTxtProductUnit().getText().trim();
        if (productUnit.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid product unit.");
            return;
        }

        java.sql.Date productDate = null;
        //...
        //Need an exception to handle expiration date

        //Makes an object for this product
        Product product = new Product();
        product.setID(ID);
        product.setName(productName);
        product.setPrice(productPrice);
        product.setQuantity(productQuantity);
        product.setDescription(productDescription);
        product.setSupplier(productSupplier);
        product.setUnit(productUnit);
        product.setExpiration(productDate);

        //Stores the product to the database
        qa.updateProduct(product, ID);
    }

    private void loadProduct() {
        int ID = 0;
        try {
            ID = Integer.parseInt(ps.getTxtProductID().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product ID.!");
            return;
        }
        Product product = qa.loadProduct(ID);
        if (product == null) {
            JOptionPane.showMessageDialog(null, "Product does not exist.");
            return;
        }

        ps.getTxtProductName().setText(product.getName());
        ps.getTxtProductPrice().setText(String.valueOf(product.getPrice()));
        ps.getTxtProductQuantity().setText(String.valueOf(product.getQuantity()));
        ps.getTxtProductDescription().setText(product.getDescription());
        ps.getTxtProductSupplier().setText(String.valueOf(product.getSupplier()));
        ps.getTxtProductUnit().setText(String.valueOf(product.getUnit()));
        ps.getTxtProductDate().setText(String.valueOf(product.getExpiration()));
    }

}

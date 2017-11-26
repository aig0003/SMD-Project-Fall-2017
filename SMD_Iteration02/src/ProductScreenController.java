import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProductScreenController implements ActionListener {
    private ProductScreenView view;
    private SQLDataAdapter adapter;

    public ProductScreenController(ProductScreenView view, SQLDataAdapter adapter){
        this.view = view;
        this.adapter = adapter;

        view.getLoadButton().addActionListener(this);
        view.getSaveButton().addActionListener(this);
        view.getClearButton().addActionListener(this);
        view.getBackButton().addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == view.getLoadButton()) { loadProduct(); }
        else if (e.getSource() == view.getSaveButton()) { saveProduct(); }
        else if(e.getSource() == view.getClearButton()) { clearProduct(); }
        else if(e.getSource() == view.getBackButton()) { back(); }
    }

    public void back() {
        clearProduct();
        Application.getInstance().getProductScreenView().setVisible(false);
        Application.getInstance().getViewInventoryScreenView().setVisible(true);
    }

    public void clearProduct() {
        view.getProductIDText().setText("");
        view.getProductNameText().setText("");
        view.getProductPriceText().setText("");
        view.getProductQuantityText().setText("");
        view.getProductDescriptionText().setText("");
        view.getProductSupplierText().setText("");
        view.getProductUnitText().setText("");
        view.getProductDateText().setText("");
    }

    public void loadProduct() {
        int ID = 0;
        try {
            ID = Integer.parseInt(view.getProductIDText().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product ID.");
            return;
        }
        Product product = adapter.loadProduct(ID);
        if (product == null) {
            JOptionPane.showMessageDialog(null, "Product does not exist.");
            return;
        }

        view.getProductNameText().setText(product.getName());
        view.getProductPriceText().setText(String.valueOf(product.getPrice()));
        view.getProductQuantityText().setText(String.valueOf(product.getQuantity()));
        view.getProductDescriptionText().setText(product.getDescription());
        view.getProductSupplierText().setText(String.valueOf(product.getSupplier()));
        view.getProductUnitText().setText(String.valueOf(product.getUnit()));
        view.getProductDateText().setText(String.valueOf(product.getExpiration()));
    }

    private void saveProduct() {
        int ID; //Check if ID is valid
        try { ID = Integer.parseInt(view.getProductIDText().getText()); }
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Invalid product ID.");return; }

        double productPrice; //Check if price is valid
        try { productPrice = Double.parseDouble(view.getProductPriceText().getText()); }
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Invalid product price.");return; }

        double productQuantity; //Check if quantity is valid
        try { productQuantity = Double.parseDouble(view.getProductQuantityText().getText()); }
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Invalid product quantity.");return; }

        String productName = view.getProductNameText().getText().trim(); //Check if name is valid
        if (productName.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid product name.");return; }

        String productDescription = view.getProductDescriptionText().getText().trim(); // Check if description is valid
        if (productDescription.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid product description.");return; }

        String productSupplier = view.getProductSupplierText().getText().trim(); // Check if supplier is valid
        if (productSupplier.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid product supplier.");return; }

        String productUnit = view.getProductUnitText().getText().trim(); // Check if unit is valid
        if (productUnit.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid product unit.");return; }

        String dateAsString = view.getProductDateText().getText().trim();
        java.sql.Date productDate = null;
        if (dateAsString.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid expiration date.");return; }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = formatter.parse(dateAsString);
            productDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid expiration date format (use 'MM-DD-YYYY').");
            e.printStackTrace();
        }

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
        adapter.saveProduct(product);
    }
}

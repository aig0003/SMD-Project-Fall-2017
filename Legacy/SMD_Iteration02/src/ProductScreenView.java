//TODO: Reformat buttons/text fields
import javax.swing.*;
import java.awt.*;

public class ProductScreenView extends JFrame {
    // String labels/Dimension Constants
    private static int PW = 600; private static int PH = 480; // Page width & height dimension
    private static int BW = 120; private static int BH = 25; // Button width & height dimensions
    private static String PAGETITLE = "Product Management";

    // Text fields
    private JTextField productIDText  = new JTextField(10);
    private JTextField productNameText  = new JTextField(15);
    private JTextField productPriceText  = new JTextField(5);
    private JTextField productQuantityText  = new JTextField(3);
    private JTextField productDescriptionText  = new JTextField(40);
    private JTextField productSupplierText  = new JTextField(15);
    private JTextField productUnitText  = new JTextField(10);
    private JTextField productDateText  = new JTextField(8);

    // Buttons
    private JButton loadButton = new JButton("Load");
    private JButton saveButton = new JButton("Save");
    private JButton clearButton = new JButton("Clear");
    private  JButton backButton = new JButton("Back");

    public ProductScreenView() {
        this.setTitle(PAGETITLE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close window on exit
        this.setSize(PW, PH);

        loadButton.setPreferredSize(new Dimension(BW, BH));
        saveButton.setPreferredSize(new Dimension(BW, BH));
        clearButton.setPreferredSize(new Dimension(BW, BH));
        backButton.setPreferredSize(new Dimension(BW, BH));

        //Button Panel
        JPanel panelButton = new JPanel();
        panelButton.add(backButton);
        panelButton.add(clearButton);
        panelButton.add(loadButton);
        panelButton.add(saveButton);
        this.getContentPane().add(panelButton);

        //TextField: Product ID
        JPanel panelProductID = new JPanel();
        panelProductID.add(new JLabel("Product ID: "));
        panelProductID.add(productIDText);
        productIDText.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelProductID);

        //textField:Product Name
        JPanel panelProductName = new JPanel();
        panelProductName.add(new JLabel("Product Name: "));
        panelProductName.add(productNameText);
        productNameText.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelProductName);

        //TextField: Price
        JPanel panelProductInfo = new JPanel();
        panelProductInfo.add(new JLabel("Price: "));
        panelProductInfo.add(productPriceText);
        productPriceText.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelProductInfo);

        //TextField: Quantity
        JPanel panelQuantity = new JPanel();
        panelQuantity.add(new JLabel("Quantity: "));
        panelQuantity.add(productQuantityText);
        productQuantityText.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelQuantity);

        //TextField: Description
        JPanel panelDescription = new JPanel();
        panelDescription.add(new JLabel("Description: "));
        panelDescription.add(productDescriptionText);
        productDescriptionText.setHorizontalAlignment(JTextField.LEFT);
        this.getContentPane().add(panelDescription);

        //TextField: Supplier
        JPanel panelSupplier = new JPanel();
        panelSupplier.add(new JLabel("Supplier: "));
        panelSupplier.add(productSupplierText);
        productSupplierText.setHorizontalAlignment(JTextField.LEFT);
        this.getContentPane().add(panelSupplier);

        //TextField: Unit/Measurements
        JPanel panelUnit = new JPanel();
        panelUnit.add(new JLabel("Unit of Measurement: "));
        panelUnit.add(productUnitText);
        productUnitText.setHorizontalAlignment(JTextField.LEFT);
        this.getContentPane().add(panelUnit);

        //TextField: Expiration date
        JPanel panelDate = new JPanel();
        panelDate.add(new JLabel("Expiration Date: "));
        panelDate.add(productDateText);
        productDateText.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelDate);

        //Adds instructions for the formatting of the date.
        JPanel instructionPanel = new JPanel();
        instructionPanel.add(new JLabel("Date is formatted: yyyy-MM-dd"));
        this.getContentPane().add(instructionPanel);
    }

    public JButton getBackButton() { return backButton;}
    public JButton getClearButton() { return clearButton;}
    public JButton getLoadButton() {
        return loadButton;
    }
    public JButton getSaveButton() {
        return saveButton;
    }

    public JTextField getProductIDText() {
        return productIDText;
    }
    public JTextField getProductNameText() { return productNameText; }
    public JTextField getProductPriceText() {
        return productPriceText;
    }
    public JTextField getProductQuantityText() {
        return productQuantityText;
    }
    public JTextField getProductDescriptionText() {
        return productDescriptionText;
    }
    public JTextField getProductSupplierText() {
        return productSupplierText;
    }
    public JTextField getProductUnitText() {
        return productUnitText;
    }
    public JTextField getProductDateText() {
        return productDateText;
    }
}

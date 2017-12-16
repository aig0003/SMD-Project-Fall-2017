import javax.swing.*;
public class ProductScreen extends JFrame{
    private JTextField txtProductID  = new JTextField(10);
    private JTextField txtProductName  = new JTextField(30);
    private JTextField txtProductPrice  = new JTextField(10);
    private JTextField txtProductQuantity  = new JTextField(10);
    private JTextField txtProductDescription  = new JTextField(120);
    private JTextField txtProductSupplier  = new JTextField(30);
    private JTextField txtProductUnit  = new JTextField(10);
    private JTextField txtProductDate  = new JTextField(10);

    private JButton btnLoad = new JButton("Load Product");
    private JButton btnSave = new JButton("Save Product");

    public ProductScreen() {
        this.setTitle("Product Screen");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(600, 600);

        JPanel panelButton = new JPanel();
        panelButton.add(btnLoad);
        panelButton.add(btnSave);
        this.getContentPane().add(panelButton);

        JPanel panelProductID = new JPanel();
        panelProductID.add(new JLabel("Product ID: "));
        panelProductID.add(txtProductID);
        txtProductID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelProductID);

        JPanel panelProductName = new JPanel();
        panelProductName.add(new JLabel("Product Name: "));
        panelProductName.add(txtProductName);
        this.getContentPane().add(panelProductName);

        JPanel panelProductInfo = new JPanel();
        panelProductInfo.add(new JLabel("Price: "));
        panelProductInfo.add(txtProductPrice);
        txtProductPrice.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelProductInfo);

        JPanel panelQuantity = new JPanel();
        panelQuantity.add(new JLabel("Quantity: "));
        panelQuantity.add(txtProductQuantity);
        txtProductQuantity.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelQuantity);


    }

    public JButton getLoadBtn() {
        return btnLoad;
    }
    public JButton getSaveBtn() {
        return btnSave;
    }

    public JTextField getTxtProductID() {
        return txtProductID;
    }
    public JTextField getTxtProductName() {
        return txtProductName;
    }
    public JTextField getTxtProductPrice() {
        return txtProductPrice;
    }
    public JTextField getTxtProductQuantity() {
        return txtProductQuantity;
    }
    public JTextField getTxtProductDescription() {
        return txtProductDescription;
    }
    public JTextField getTxtProductSupplier() {
        return txtProductSupplier;
    }
    public JTextField getTxtProductUnit() {
        return txtProductUnit;
    }
    public JTextField getTxtProductDate() {
        return txtProductDate;
    }

}

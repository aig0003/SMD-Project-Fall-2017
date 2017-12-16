import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckoutScreenController implements ActionListener {
    private CheckoutScreenView view;
    private SQLDataAdapter adapter; // to save and load product
    private ReceiptBuilder TXTbuilder; // For printing TXT receipt
    private ReceiptBuilder HTMLbuilder;
    private Order order = null;
    private Product product = null;
    private double quantity = 0;
    private DecimalFormat format = new DecimalFormat("0.00");


    public CheckoutScreenController(CheckoutScreenView view, SQLDataAdapter adapter, ReceiptBuilder TXTBuilder, ReceiptBuilder HTMLBuilder) {
        this.adapter = adapter;
        this.view = view;
        this.TXTbuilder = TXTBuilder;
        this.HTMLbuilder = HTMLBuilder;

        view.getAddButton().addActionListener(this);
        view.getRemoveButton().addActionListener(this);
        view.getPrintHTMLButton().addActionListener(this);
        view.getPrintTXTButton().addActionListener(this);
        view.getBackButton().addActionListener(this);

        order = new Order();
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == view.getAddButton()){ addOrderEntry();}
        else if (e.getSource() == view.getPrintTXTButton() || e.getSource() == view.getPrintHTMLButton()){
            if (e.getSource() == view.getPrintTXTButton()) {
                int orderID = saveOrder();
                //saveOrder(); printTable(view.getTable());
                String TXTreceiptStr = buildReceiptText(orderID);
                printReceiptText(TXTreceiptStr, orderID);
            }
            else if (e.getSource() == view.getPrintHTMLButton()) {
                int orderID = saveOrder();
                String HTMLreceiptStr = buildReceiptHTML(orderID);
                printReceiptHTML(HTMLreceiptStr, orderID);
            }
        }
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

    private int saveOrder() {
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
        return orderID;
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

    public String buildReceiptText(int orderID) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        DecimalFormat decimalFormat = new DecimalFormat("####,###,##0.00");

        Employee cashier = Application.getCurrentUser();
        TXTbuilder.setHeader("SMD Store", orderID, cashier.getUsername(), dateFormat.format(date));
        double totalTax = 0;
        double totalCost = 0;
        //String productID, String productName, String quantity, String price
        for(int i = 0; i < view.getEntries(); i++) {
            TXTbuilder.addLine(
                    view.getRowAt(i)[0],
                    view.getRowAt(i)[1],
                    decimalFormat.format(Double.parseDouble(view.getRowAt(i)[3])),
                    decimalFormat.format(Double.parseDouble(view.getRowAt(i)[4])));
            totalTax += Double.parseDouble(view.getRowAt(i)[3]) * Double.parseDouble(view.getRowAt(i)[4]) * 0.09;
            totalCost += Double.parseDouble(view.getRowAt(i)[3]) * Double.parseDouble(view.getRowAt(i)[4]);
        }
        //String cost, String tax, String finalCost

        double tax = 0.09;
        String costStr = decimalFormat.format(totalCost);
        String totalTaxStr = decimalFormat.format(totalTax);
        String costWithTax = decimalFormat.format(totalCost + totalTax);

        TXTbuilder.setFooter(costStr, totalTaxStr, costWithTax);
        //System.out.println(builder.toString());
        String res = TXTbuilder.toString();
        TXTbuilder.clearStringBuilder(); // Prevents overlaying multiple receipts
        return res;
    }

    public String buildReceiptHTML(int orderID) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        DecimalFormat decimalFormat = new DecimalFormat("####,###,##0.00");

        Employee cashier = Application.getCurrentUser();
        HTMLbuilder.setHeader("SMD Store", orderID, cashier.getUsername(), dateFormat.format(date));
        double totalTax = 0;
        double totalCost = 0;
        //String productID, String productName, String quantity, String price
        for(int i = 0; i < view.getEntries(); i++) {
            HTMLbuilder.addLine(
                    view.getRowAt(i)[0],
                    view.getRowAt(i)[1],
                    decimalFormat.format(Double.parseDouble(view.getRowAt(i)[3])),
                    decimalFormat.format(Double.parseDouble(view.getRowAt(i)[4])));
            totalTax += Double.parseDouble(view.getRowAt(i)[3]) * Double.parseDouble(view.getRowAt(i)[4]) * 0.09;
            totalCost += Double.parseDouble(view.getRowAt(i)[3]) * Double.parseDouble(view.getRowAt(i)[4]);
        }
        //String cost, String tax, String finalCost

        double tax = 0.09;
        String costStr = decimalFormat.format(totalCost);
        String totalTaxStr = decimalFormat.format(totalTax);
        String costWithTax = decimalFormat.format(totalCost + totalTax);

        HTMLbuilder.setFooter(costStr, totalTaxStr, costWithTax);
        //System.out.println(builder.toString());
        String res = HTMLbuilder.toString();
        HTMLbuilder.clearStringBuilder(); // Prevents overlaying multiple receipts
        return res;
    }

    public void printReceiptText(String receiptStr, int orderID) {
        try {
            String fileName = "receipt" + orderID + ".txt";
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(receiptStr);
            printWriter.close();
            JOptionPane.showMessageDialog(null, "Receipt saved as " + fileName);
        } catch(IOException e) {
            System.out.println("ERROR: Could not print receipt to .txt file");
        }
    }

    public void printReceiptHTML(String receiptStr, int orderID) {
        try {
            String fileName = "receipt" + orderID + ".htm";
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("<span>"+receiptStr+"</span>");
            writer.close();
            JOptionPane.showMessageDialog(null, "Receipt saved as " + fileName);
        } catch (Exception e) {
            System.out.println("ERROR: Could not print receipt to .html file");
        }
    }
}

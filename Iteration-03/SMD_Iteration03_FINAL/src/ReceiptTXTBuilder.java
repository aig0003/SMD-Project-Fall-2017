import java.text.DecimalFormat;

public class ReceiptTXTBuilder implements ReceiptBuilder {
    StringBuilder str = new StringBuilder();

    @Override
    public void setHeader(String storeName, int orderID, String cashier, String date) {
        str.append("----------------------------------\n");
        str.append(storeName); str.append('\n');
        str.append("Order Number: " + String.valueOf(orderID) + "\n");
        str.append("Cashier: " + cashier + "\n");
        str.append("Date: " + date + "\n");
        str.append("----------------------------------\n");
        str.append("ProdID,  Product,  Quantity,  Price,  Total,  Tax\n");

    }

    @Override
    public void addLine(String productID, String productName, String quantity, String price) {
        DecimalFormat decimalFormat = new DecimalFormat("####,###,##0.00");

        str.append(productID); str.append("    ");
        str.append(productName); str.append("    ");
        str.append(quantity); str.append("    ");
        str.append(price); str.append("    ");
        str.append(decimalFormat.format(Double.parseDouble(quantity) * Double.parseDouble(price))); str.append("    ");
        str.append(decimalFormat.format(Double.parseDouble(quantity) * Double.parseDouble(price) * 0.09)); str.append('\n');
    }

    @Override
    public void setFooter(String cost, String tax, String finalCost) {
        str.append("----------------------------------\n");
        str.append("Total (pre-tax): $" + cost + "\n");
        str.append("Total tax: $" + tax + "\n");
        str.append("Total: $" + finalCost + "\n");
    }

    @Override
    public String toString() {
        return str.toString();
    }

    @Override
    public void clearStringBuilder() {
        str.setLength(0);
    }
}

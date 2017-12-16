import java.text.DecimalFormat;

public class ReceiptHTMLBuilder implements ReceiptBuilder {
    StringBuilder str = new StringBuilder();

    @Override
    public void setHeader(String storeName, int orderID, String cashier, String date) {
        str.append("----------------------------------<br>");
        str.append(storeName); str.append("<br>");
        str.append("Order Number: " + String.valueOf(orderID) + "<br>");
        str.append("Cashier: " + cashier + "<br>");
        str.append("Date: " + date + "<br>");
        str.append("----------------------------------<br>");
        str.append("ProdID,  Product,  Quantity,  Price,  Total,  Tax<br>");

    }

    @Override
    public void addLine(String productID, String productName, String quantity, String price) {
        DecimalFormat decimalFormat = new DecimalFormat("####,###,##0.00");

        str.append(productID); str.append("    ");
        str.append(productName); str.append("    ");
        str.append(quantity); str.append("    ");
        str.append(price); str.append("    ");
        str.append(decimalFormat.format(Double.parseDouble(quantity) * Double.parseDouble(price))); str.append("    ");
        str.append(decimalFormat.format(Double.parseDouble(quantity) * Double.parseDouble(price) * 0.09)); str.append("<br>");
    }

    @Override
    public void setFooter(String cost, String tax, String finalCost) {
        str.append("----------------------------------<br>");
        str.append("Total (pre-tax): $" + cost + "<br>");
        str.append("Total tax: $" + tax + "<br>");
        str.append("Total: $" + finalCost + "<br>");
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

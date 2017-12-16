public interface ReceiptBuilder {
    public void setHeader(String storeName, int orderID, String cashier, String date);
    public void addLine(String productID, String productName, String quantity, String price);
    public void setFooter(String cost, String tax, String finalCost);
    public String toString();
    public void clearStringBuilder();
}

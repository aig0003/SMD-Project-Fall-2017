import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class Order {
    private int orderID;
    private String customerName;
    private double totalCost;
    private double totalTax;
    private Date date;

    private List<OrderLine> orderLine;

    public Order() {
        orderLine = new ArrayList<>();
    }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalTax() {
        return totalTax;
    }
    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void addLine(OrderLine line) {
        orderLine.add(line);
    }
    public void removeLine(OrderLine line) {
        orderLine.remove(line);
    }

    public List<OrderLine> getCheckoutLine() {
        return orderLine;
    }
}

//TODO: Still need to figure out the data stuff...
import java.sql.Date;

public class Product {
    private int id;
    private String name;
    private double price;
    private double quantity;
    private String description;
    private String supplier;
    private String measurement;
    private java.sql.Date date;

    public int getID(){return id;}
    public void setID(int id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public double getPrice(){return price;}
    public void setPrice(double price){this.price = price;}

    public double getQuantity(){return quantity;}
    public void setQuantity(double quantity){this.quantity = quantity;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public String getSupplier(){return supplier;}
    public void setSupplier(String supplier){this.supplier = supplier;}

    public String getUnit(){return measurement;}
    public void setUnit(String measurement){this.measurement = measurement;}

    public Date getExpiration(){return date;}
    public void setExpiration(Date date){this.date = date;}

}

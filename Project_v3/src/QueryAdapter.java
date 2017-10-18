import java.sql.*;
import java.sql.Date;
/**
 * QueryAdapter.java
 * Acts like a "Middleman" between the Application and the controllers,
 *  handles SQL statements to turn SQL elements into usable java objects
 */
public class QueryAdapter {
    private Connection connection;
    public QueryAdapter(Connection connection){this.connection = connection;}

    //
    //  Accepts: id:int. The ID number which is used to designate product information
    //  Returns: Product/null. Product from SQLite DB or null if invalid ID
    public Product loadProduct(int id) {
        try{
            String selectFromID = "SELECT * FROM storeItem WHERE ID = " + id;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectFromID);//Loads product info from SQLDB based on ID inputted.

            if(rs.next()){ //steps through loaded result and inputs SQL info of product into java objects.
                Product product = new Product();
                product.setID(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getDouble(3));
                product.setQuantity(rs.getDouble(4));//Don't understand why this is double but instructor said so.
                product.setDescription(rs.getString(5));
                product.setSupplier(rs.getString(6));
                product.setUnit(rs.getString(7));
                product.setExpiration(rs.getDate(8));

                rs.close();
                stmt.close();

                return product;
            }
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database");
            e.printStackTrace();
        }
        return null;
    }

    public void saveProduct(Product product){
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Product WHERE ID = ?");
            pstmt.setInt(1,product.getID());
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){ //Done if product exists
                pstmt = connection.prepareStatement("UPDATE Product SET ");
                pstmt.setString(1, product.getName());
                pstmt.setDouble(2, product.getPrice());
                pstmt.setDouble(3, product.getQuantity());
                pstmt.setInt(4, product.getID());
                pstmt.setString(5, product.getDescription());
                pstmt.setString(6, product.getSupplier());
                pstmt.setString(7, product.getUnit());
                pstmt.setDate(8, product.getExpiration());

                //...
            } else { //Product did not previously exist
                pstmt = connection.prepareStatement("INSERT INTO Product VALUES (?, ?, ?, ?)"); //...
                pstmt.setString(2, product.getName());
                pstmt.setDouble(3, product.getPrice());
                pstmt.setDouble(4, product.getQuantity());
                pstmt.setString(5, product.getDescription());
                pstmt.setString(6, product.getSupplier());
                pstmt.setString(7, product.getUnit());
                pstmt.setDate(8, product.getExpiration());
                pstmt.setInt(1, product.getID());
                //...
            }
            pstmt.execute();

            rs.close();
            pstmt.close();

        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database");
            e.printStackTrace();
        }
    }
}

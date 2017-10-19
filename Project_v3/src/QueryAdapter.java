import java.sql.*;
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
        try {
            String selectFromID = "SELECT * FROM storeItem WHERE ID = " + id;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectFromID);//Loads product info from SQLDB based on ID inputted.

            if (rs.next()) { //steps through loaded result and inputs SQL info of product into java objects.
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
        } catch (SQLException e) {
            System.out.println("ERROR: Could not access SQL Database");
            e.printStackTrace();
        }
        return null;
    }

    //Updates the information for a given product to match the parameter in SQL.
    //Also takes the old product id in case that is what you are changing.
    public int updateProduct(Product product, int productId) {
        try {
            //Verifies the item that will be changed actually exists. If not it will create a new item later on.
            String queryText = "SELECT * FROM storeItem WHERE ID = " + productId + ";";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryText);
            if (rs.next() && product != null) {
                queryText = "UPDATE storeItem SET ";
                queryText += "ID = " + product.getID() + ", ";
                queryText += "name = \'" + product.getName() + "\', ";
                queryText += "price = " + product.getPrice() + ", ";
                queryText += "quantity = " + product.getQuantity() + ", ";
                queryText += "description = \'" + product.getDescription() + "\', ";
                queryText += "supplier = \'" + product.getSupplier() + "\', ";
                queryText += "measurement = \'" + product.getUnit() + "\', ";
                queryText += "expirationDate = julianday(\'" + product.getExpiration() + "\')";
                queryText += " WHERE ID = " + productId + ";";
                stmt = connection.createStatement();
                stmt.executeUpdate(queryText);
            } else {
                //Gets the current maximum id and then adds on to it for the new item.
                queryText = "SELECT MAX(ID) FROM storeItem";
                stmt = connection.createStatement();
                rs = stmt.executeQuery(queryText);

                if (rs.next()) {
                    int newID = rs.getInt(1) + 1;
                    rs.close();

                    queryText = "INSERT INTO storeItem VALUES (" +
                            newID + "," +
                            "\'Please Enter a Value\', " +
                            "0.00, " +
                            "0.00, " +
                            "\'Please Enter a Value\', " +
                            "\'Please Enter a Value\', " +
                            "\'Please Enter a Value\', " +
                            "julianday(\'1970-01-01\'));";
                    stmt = connection.createStatement();
                    stmt.executeUpdate(queryText);
                    return newID; //This is the new product's given id.
                } else {
                    throw new RuntimeException();
                }
            }
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println("ERROR: Could not access SQL Database");
            e.printStackTrace();
        }
        return 0; //Normal exit code that represents that it was a normal update.
    }

    //Removes an entry from the database for the given id.
    public void remove(int itemId) {
        try {
            String queryText = "DELETE FROM storeItem WHERE ID = " + itemId + ";";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(queryText);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("ERROR: Could not access SQL Database");
            e.printStackTrace();
        }
    }

    //Gets all of the products in the SQL database and returns an array of products containining each one.
    public Product[] getAll() {
        Product[] result = new Product[0];
        try {
            //Gets the length of the database
            String selectAllLength = "SELECT Count(*) FROM storeItem";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectAllLength);
            if (rs.next()) {
                int numProducts = rs.getInt(1);
                result = new Product[numProducts];
            }

            //Does the actual query for all of the items in the database.
            String selectAllQuery = "SELECT * FROM storeItem";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectAllQuery);
            int count = 0;
            while (rs.next()) { //steps through loaded result and inputs SQL info of product into java objects.
                Product product = new Product();
                product.setID(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getDouble(3));
                product.setQuantity(rs.getDouble(4));//Don't understand why this is double but instructor said so.
                product.setDescription(rs.getString(5));
                product.setSupplier(rs.getString(6));
                product.setUnit(rs.getString(7));
                product.setExpiration(rs.getDate(8));
                result[count] = product;
                count++;
            }
            rs.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            System.out.println("ERROR: Could not access SQL Database");
            e.printStackTrace();
        }
        return null;
    }
}

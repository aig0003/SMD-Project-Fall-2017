import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * SQLDataAdapter.java
 *
 * Acts like a "Middleman" between the Application and the controllers,
 *  handles SQL statements to turn SQL elements into usable java objects
 */
public class SQLDataAdapter {

    public SQLDataAdapter (){
    }

    //Allows for concurrent database access by only creating connections for the duration of database access.
    private Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:store.db");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: SQLite is not installed");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("ERROR: SQLite database is not ready");
            System.exit(2);
        }
        return connection;
    }

    // Accepts: id:int. The ID number which is used to designate product information
    //  Returns: Product/null. Product from SQLite DB or null if invalid ID
    public Product loadProduct(int id) {
        Connection connection = createConnection();
        try {
            String selectFromID = "SELECT * FROM StoreItem WHERE ID = " + id;
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
                try { //Dates are dumb
                    String date = String.valueOf(rs.getObject(8));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //Use M not m so it is months not minutes.
                    java.util.Date utilDate = formatter.parse(date);
                    java.sql.Date sqlStartDate = new java.sql.Date(utilDate.getTime());
                    product.setExpiration(sqlStartDate);
                } catch (ParseException e) {
                    System.out.println("ERROR: Error parsing the date");
                    e.printStackTrace();
                }

                rs.close();
                stmt.close();
                connection.close();
                return product;
            }
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database to load product " + id);
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveProduct(Product product){
        Connection connection = createConnection();
        try {
            String query = "SELECT * FROM StoreItem WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, product.getID());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){ //Done if product exists
                int id = product.getID();
                query = "UPDATE StoreItem SET ID = ?, name = ?, price = ?, quantity = ?, description = ?, supplier=?, measurement = ?, expirationDate = ? WHERE ID = " + id;
                pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, product.getID());           // ID
                pstmt.setString(2, product.getName());      // Name
                pstmt.setDouble(3, product.getPrice());     //Price
                pstmt.setDouble(4, product.getQuantity());  // Quantity
                pstmt.setString(5, product.getDescription()); // Description
                pstmt.setString(6, product.getSupplier());  //Supplier
                pstmt.setString(7, product.getUnit());      //Measurement
                pstmt.setString(8, String.valueOf(product.getExpiration()));  //Expiration
                //...
            } else { //Product did not previously exist
                pstmt = connection.prepareStatement("INSERT INTO StoreItem VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); //...
                pstmt.setInt(1, product.getID());
                pstmt.setString(2, product.getName());
                pstmt.setDouble(3, product.getPrice());
                pstmt.setDouble(4, product.getQuantity());
                pstmt.setString(5, product.getDescription());
                pstmt.setString(6, product.getSupplier());
                pstmt.setString(7, product.getUnit());
                pstmt.setDate(8, product.getExpiration());
                //...
            }

            pstmt.execute();

            rs.close();
            pstmt.close();
            connection.close();
            return true;
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database to save product ");
            e.printStackTrace();
            return false;
        }
    }

    public Object[] loadOrderLine(int rowNumber) {
        Connection connection = createConnection();
        try{
            String selectFromRow = "SELECT * FROM OrderCheckout WHERE rowid  = " + rowNumber;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectFromRow);//Loads product info from SQLDB based on ID inputted.

            if(rs.next()){ //steps through loaded result and inputs SQL info of product into java objects.
                Object[] row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getDouble(3);
                DecimalFormat format = new DecimalFormat("0.00");
                row[3] = format.format(rs.getDouble(4));

                rs.close();
                stmt.close();
                connection.close();
                return row;
            }
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database to load row " + rowNumber);
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveOrder(OrderLine line) {
        Connection connection = createConnection();
        try {
            String query = "SELECT * FROM StoreItem";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            pstmt = connection.prepareStatement("INSERT INTO OrderCheckout VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, line.getOrderID());
            pstmt.setInt(2, line.getProductID());
            pstmt.setDouble(3, line.getQuantity());
            pstmt.setDouble(4, line.getCost());

            pstmt.execute();

            rs.close();
            pstmt.close();
            connection.close();
            return true;
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database to save product ");
            e.printStackTrace();
            return false;
        }
    }

    public int getEntries() {
        Connection connection = createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM OrderCheckout");
            rs.next();
            int rowCount = rs.getInt(1);
            rs.close() ;
            stmt.close();
            connection.close();
            return rowCount;
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL DB to retrieve number of entries in  OrderCheckout DB");
            e.printStackTrace();
            return 0;
        }
    }


    public Product[] getAll() {
        Connection connection = createConnection();
        Product[] result = new Product[0];
        try {
            //Gets the length of the database
            String selectAllLength = "SELECT Count(*) FROM StoreItem";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectAllLength);
            if (rs.next()) {
                int numProducts = rs.getInt(1);
                result = new Product[numProducts];
            }

            //Does the actual query for all of the items in the database.
            String selectAllQuery = "SELECT * FROM StoreItem";
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
                try { //Dates are dumb
                    String date = String.valueOf(rs.getObject(8));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //Use M not m so it is months not minutes.
                    java.util.Date utilDate = formatter.parse(date);
                    java.sql.Date sqlStartDate = new java.sql.Date(utilDate.getTime());
                    product.setExpiration(sqlStartDate);
                } catch (ParseException e) {
                    System.out.println("ERROR: Error parsing the date");
                    e.printStackTrace();
                }
                result[count] = product;
                count++;
            }
            rs.close();
            stmt.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            System.out.println("ERROR: Could not access SQL Database");
            e.printStackTrace();
        }
        return null;
    }

    public Employee loadEmployee(String username) {
        Connection connection = createConnection();
        try {
            String selectFromUsername = "SELECT * FROM Employee WHERE username = '" + username + "'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectFromUsername);

            if(rs.next()){ //steps through loaded result and inputs SQL info of employee into java objects.
                Employee employee = new Employee();

                employee.setUsername(rs.getString(1));
                employee.setPassword(rs.getString(2));
                employee.setName(rs.getString(3));
                employee.setProfilePicURL(rs.getString(4));
                if (employee.getProfilePicURL() == null) {
                    employee.setProfilePicURL(System.getProperty("user.dir") + "/images/dummy.jpg");
                }
                employee.setEmployeeType(rs.getInt(5));

                rs.close();
                stmt.close();
                connection.close();
                return employee;
            }
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database to load employee \"" + username + "\"");
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveEmployee(Employee employee) {
        Connection connection = createConnection();
        try {
            String query = "SELECT * FROM Employee WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, employee.getUsername());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){ //Done if employee exists
                String username = employee.getUsername();
                query = "UPDATE Employee SET username = ?, password = ?, name = ?, profilePicURL = ?, emplType = ? WHERE username = '" + username + "'";
                pstmt = connection.prepareStatement(query);
                pstmt.setString(1, employee.getUsername());           // username
                pstmt.setString(2, employee.getPassword());      // password
                pstmt.setString(3, employee.getName());     //name
                pstmt.setString(4, employee.getProfilePicURL());  // profile pic
                pstmt.setInt(5, employee.getEmployeeType()); // employee Type; 0 = Cashier, 1 = Manager

            } else { //Employee did not previously exist
                pstmt = connection.prepareStatement("INSERT INTO Employee VALUES (?, ?, ?, ?, ?)");
                pstmt.setString(1, employee.getUsername());           // username
                pstmt.setString(2, employee.getPassword());      // password
                pstmt.setString(3, employee.getName());     //name
                pstmt.setString(4, System.getProperty("user.dir") + "/images/dummy.jpg");
                pstmt.setInt(5, employee.getEmployeeType()); // employee Type; 0 = Cashier, 1 = Manager
            }

            pstmt.execute();

            rs.close();
            pstmt.close();
            connection.close();
            return true;
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database to save employee");
            e.printStackTrace();
            return false;
        }
    }

    //
    //  Checks Employee table if username previously exists in database. Returns true if so, false otherwise
    //
    public boolean employeeExists(String username) {
        Connection connection = createConnection();
        try {
            String selectFromUsername = "SELECT * FROM Employee WHERE username = '" + username + "'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectFromUsername);
            if(rs.next()) {
                rs.close();
                stmt.close();
                connection.close();
                return true;
            } // Username exists –> ret true
            else {
                connection.close();
                return false;
            } // Username exists –> ret false
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database to cross-reference employee data");
            e.printStackTrace();
            return false;
        }
    }

    //
    // Checks if the given username and password correspond to a user. Then returns the employee.
    //
    public Employee tryLogin(String username, String password) {
        Connection connection = createConnection();
        try{
            String selectFromUsername = "SELECT * FROM Employee WHERE username = '" + username + "' AND password = '" + password + "';";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectFromUsername);

            if(rs.next()){ //steps through loaded result and inputs SQL info of employee into java objects.
                Employee employee = new Employee();

                employee.setUsername(rs.getString(1));
                employee.setPassword(rs.getString(2));
                employee.setName(rs.getString(3));
                employee.setProfilePicURL(rs.getString(4));
                if (employee.getProfilePicURL() == null) {
                    employee.setProfilePicURL(System.getProperty("user.dir") + "/images/dummy.jpg");
                }
                employee.setEmployeeType(rs.getInt(5));

                rs.close();
                stmt.close();
                connection.close();
                return employee;
            }
        } catch(SQLException e) {
            System.out.println("ERROR: Could not access SQL Database to log in user \"" + username + "\"");
            e.printStackTrace();
        }
        return null;
    }

    //
    //Removes an entry from the database for the given id.
    //
    public void remove(int itemId) {
        Connection connection = createConnection();
        try {
            String queryText = "DELETE FROM storeItem WHERE ID = " + itemId + ";";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(queryText);
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("ERROR: Could not access SQL Database");
            e.printStackTrace();
        }
    }

}

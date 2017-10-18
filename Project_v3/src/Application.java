import java.sql.*;

/**
 * Application.java
 * Handles try-catching of SQLite DB connection as well as instantiates
 *  most presentation/controller classes.
 * Contains main class
 */
public class Application {
    //Singleton Pattern – Restricts instantiation of the Application to
    //  be exactly one object
    private static Application instance;
    public static Application getInstance(){
        if(instance==null){instance=new Application();}return instance;
    }

    //Main Components of Application
    private Connection connection; //Connects application to SQLite DB
    public Connection getConnection(){return connection;}

    private QueryAdapter qAdapter; // Connects Query to SQLite DB
    public QueryAdapter getQuery(){return qAdapter;}

    //Instances of each presentable class, along with getter's so each class
    //  can access the screens as needed
    private StoreManagementSystem sms;
    public StoreManagementSystem getSMSSceen(){return sms;}


    private ProductController productController;
    public ProductController getProductController(){return productController; }

    private ProductScreen productScreen;
    public ProductScreen getProductScreen(){return productScreen;}

    private CheckoutController checkoutController;
    public CheckoutController getCheckoutController(){return checkoutController;}

    private CheckoutScreen checkoutScreen;
    public CheckoutScreen getCheckoutScreen(){return checkoutScreen;}


    //...

    private Application(){
        // creates SQLite DB connection
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:store.db");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite is not installed. System exits with error!");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("SQLite database is not ready. System exits with error!");
            System.exit(2);
        }

        //Creates QueryAdapter connected to the SQLite DB connection
        qAdapter = new QueryAdapter(connection);

        //Create presentation and controller classes here!
        productScreen = new ProductScreen();
        productController = new ProductController(productScreen, qAdapter);
        sms = new StoreManagementSystem();

    }

    public static void main(String[] args) {
        Application.getInstance().getSMSSceen().setVisible(true);
    }

}

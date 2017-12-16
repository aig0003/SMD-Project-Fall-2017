import java.sql.DriverManager;

public class ProxySQLDataAdapter implements IDataAccess {
    String hostName;
    int portNumber;

    private SQLDataAdapter getLocalSQLDataAdapter() {
        if (localSQLDataAdapter == null) {
            try {
                localSQLDataAdapter = new SQLDataAdapter();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return localSQLDataAdapter;
    }

    private RemoteSQLDataAdapter getRemoteSQLDataAdapter() {
        if (remoteSQLDataAdapter == null)
            System.out.println("No access to remote data server");
        System.out.println("Attempting to connect to host " + hostName + " on port " + portNumber);

        remoteSQLDataAdapter = new RemoteSQLDataAdapter(hostName, portNumber);
        return remoteSQLDataAdapter;
    }

    SQLDataAdapter localSQLDataAdapter = null;
    RemoteSQLDataAdapter remoteSQLDataAdapter = null;

    public ProxySQLDataAdapter(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public Product loadProduct(int id) {
        Product product;
        product = getLocalSQLDataAdapter().loadProduct(id);
        if (product == null) {
            product = getRemoteSQLDataAdapter().loadProduct(id);
            if (product != null)
                localSQLDataAdapter.saveProduct(product);
        }
        return product;
    }

    public boolean saveProduct(Product product) {
        return getRemoteSQLDataAdapter().saveProduct(product);
    }

}

import javax.swing.*;

public class RestrictedDataAdapter implements IDataAccess {
    Employee user;
    IDataAccess dataAccess;

    public RestrictedDataAdapter(Employee user, IDataAccess dataAccess) {

    }

    @Override
    public Product loadProduct(int id) {
        return dataAccess.loadProduct(id);
    }

    @Override
    public boolean saveProduct(Product product) {
        if (user.getEmployeeType() == 1)
            return dataAccess.saveProduct(product);
        else
            JOptionPane.showMessageDialog(null, "This user is not authorized to save a product");
        return false;
    }
}

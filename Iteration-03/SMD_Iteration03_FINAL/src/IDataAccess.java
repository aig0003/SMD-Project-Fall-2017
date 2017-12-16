public interface IDataAccess {

    Product loadProduct(int id);

    boolean saveProduct(Product product);

//    Order loadOrder(int id);

//    boolean saveOrder(Order order);

//    User loadUser(String username, String password);

//    boolean saveUser(User user);
}
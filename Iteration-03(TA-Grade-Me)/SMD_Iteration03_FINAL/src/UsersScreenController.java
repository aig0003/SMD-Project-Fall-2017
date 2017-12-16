import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsersScreenController implements ActionListener {
    private UsersScreenView view;
    private SQLDataAdapter adapter;

    private String originalPassword = "";

    public UsersScreenController(UsersScreenView view, SQLDataAdapter adapter) {
        this.view = view;
        this.adapter = adapter;

        view.getBackButton().addActionListener(this);
        view.getLoadButton().addActionListener(this);
        view.getSaveButton().addActionListener(this);
        view.getClearButton().addActionListener(this);
        view.getCreateButton().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getLoadButton()) { loadEmployee(); }
        else if (e.getSource() == view.getSaveButton()) { saveEmployee(); }
        else if(e.getSource() == view.getClearButton()) { clearEmployee(); }
        else if(e.getSource() == view.getBackButton()) { back(); }
        else if(e.getSource() == view.getCreateButton()) { createEmployee();}
    }

    public void back() {
        Application.getInstance().getUsersScreenView().setVisible(false);
        Application.getInstance().getStoreManagementSystemView().setVisible(true);
    }

    public void clearEmployee() {
        view.getEmployeeUsernameText().setText("");
        view.getEmployeePasswordText().setText("");
        view.getEmployeeNameText().setText("");
        view.getEmployeeTypeText().setText("");
    }

    public void loadEmployee() {
        String username = view.getEmployeeUsernameText().getText().trim();
        if (username.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid username.");return; }

        Employee employee = adapter.loadEmployee(username);
        if (employee == null) {
            JOptionPane.showMessageDialog(null, "Employee does not exist.");
            return;
        }

        view.getEmployeeUsernameText().setText(employee.getUsername());
        originalPassword = employee.getPassword();
        String mask = originalPassword.replaceAll(".", "*");
        view.getEmployeePasswordText().setText(mask);
        view.getEmployeeNameText().setText(employee.getName());
        view.getEmployeeTypeText().setText(String.valueOf(employee.getEmployeeType()));
    }

    private void saveEmployee() {
        String username = view.getEmployeeUsernameText().getText().trim();
        if (username.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid username.");return; }

        String name = view.getEmployeeNameText().getText().trim();
        if (name.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid name.");return; }

        int type;
        try { type = Integer.parseInt(view.getEmployeeTypeText().getText()); //Check if name is valid
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Invalid employee type.");return; }

        Employee employee = new Employee();

        employee.setUsername(username);
        employee.setPassword(originalPassword);
        employee.setName(name);
        employee.setEmployeeType(type);

        //Stores the product to the database
        adapter.saveEmployee(employee);
        JOptionPane.showMessageDialog(null, "User info saved. To change password or profile "+
                "picture, have user access their profile page");
    }

    public void createEmployee() {
        String username = view.getEmployeeUsernameText().getText().trim();
        if (username.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid username.");return; }
        else if (adapter.employeeExists(username)) { JOptionPane.showMessageDialog(null, "User already exists.");return; }

        String password = "password";

        String name = view.getEmployeeNameText().getText().trim();
        if (name.length() == 0) { JOptionPane.showMessageDialog(null, "Invalid name.");return; }

        int type;
        try { type = Integer.parseInt(view.getEmployeeTypeText().getText()); //Check if name is valid
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Invalid employee type.");return; }

        Employee employee = new Employee();

        employee.setUsername(username);
        employee.setPassword(password);
        employee.setName(name);
        employee.setEmployeeType(type);

        //Stores the product to the database
        adapter.saveEmployee(employee);
        JOptionPane.showMessageDialog(null, "User created, Password is set to \'password\' by"+
                " default, can change this in the user's profile menu");
    }
}

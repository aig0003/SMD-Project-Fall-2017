import javax.swing.*;
import java.awt.*;

public class UsersScreenView extends JFrame {
    // String labels/Dimension Constants
    private static int PW = 600; private static int PH = 480; // Page width & height dimension
    private static int BW = 120; private static int BH = 25; // Button width & height dimensions
    private static String PAGETITLE = "User Management";

    // Text fields
    private JTextField employeeUsernameText  = new JTextField(16);
    private JLabel employeePasswordText  = new JLabel();
    private JTextField employeeNameText = new JTextField(16);
    private JTextField employeeTypeText  = new JTextField(2);
    // Buttons
    private JButton loadButton = new JButton("Load");
    private JButton saveButton = new JButton("Save");
    private JButton createButton = new JButton("Create");
    private JButton clearButton = new JButton("Clear");
    private  JButton backButton = new JButton("Back");

    public UsersScreenView() {
        this.setTitle(PAGETITLE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close window on exit
        this.setSize(PW, PH);

        backButton.setPreferredSize(new Dimension(60, BH));
        loadButton.setPreferredSize(new Dimension(BW, BH));
        saveButton.setPreferredSize(new Dimension(BW, BH));
        clearButton.setPreferredSize(new Dimension(BW, BH));
        createButton.setPreferredSize(new Dimension(BW, BH));

        //Button Panel
        JPanel panelButton = new JPanel();
        panelButton.add(backButton);
        panelButton.add(clearButton);
        panelButton.add(loadButton);
        panelButton.add(saveButton);
        panelButton.add(createButton);
        this.getContentPane().add(panelButton);

        //Label
        JPanel instructionPanel = new JPanel();
        instructionPanel.add(new JLabel("Input username and LOAD to search users"));
        this.getContentPane().add(instructionPanel);

        //TextField: Employee Username
        JPanel panelEmployeeUsername= new JPanel();
        panelEmployeeUsername.add(new JLabel("Username: "));
        panelEmployeeUsername.add(employeeUsernameText);
        employeeUsernameText.setHorizontalAlignment(JTextField.LEFT);
        this.getContentPane().add(panelEmployeeUsername);

        //TextField: Employee Password
        JPanel employeePasswordPanel= new JPanel();
        employeePasswordPanel.add(new JLabel("Password: "));
        employeePasswordPanel.add(employeePasswordText);
        employeePasswordText.setHorizontalAlignment(JTextField.LEFT);
        this.getContentPane().add(employeePasswordPanel);

        JPanel noticePanel = new JPanel();
        noticePanel.add(new JLabel("*Note: Password is hidden, have employee login to view/change it"));
        this.getContentPane().add(noticePanel);

        //TextField: Employee name
        JPanel panelEmployeeName= new JPanel();
        panelEmployeeName.add(new JLabel("Name: "));
        panelEmployeeName.add(employeeNameText);
        employeeNameText.setHorizontalAlignment(JTextField.LEFT);
        this.getContentPane().add(panelEmployeeName);

        //TextField: Employee type
        JPanel panelEmployeeType = new JPanel();
        panelEmployeeType.add(new JLabel("Employee Type (0 for Cashier, 1 for manager): "));
        panelEmployeeType.add(employeeTypeText);
        employeeTypeText.setHorizontalAlignment(JTextField.LEFT);
        this.getContentPane().add(panelEmployeeType);
    }

    public JButton getBackButton() { return backButton;}
    public JButton getClearButton() { return clearButton;}
    public JButton getLoadButton() { return loadButton; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getCreateButton() { return createButton; }

    public JTextField getEmployeeUsernameText() {
        return employeeUsernameText;
    }
    public JLabel getEmployeePasswordText() { return employeePasswordText; }
    public JTextField getEmployeeNameText() {
        return employeeNameText;
    }
    public JTextField getEmployeeTypeText() {
        return employeeTypeText;
    }

}


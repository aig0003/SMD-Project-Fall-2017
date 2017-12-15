import javax.swing.*;
import java.awt.*;

public class LoginScreenView extends JFrame{
    // String Labels/Constants
    private static int PW = 600; private static int PH = 240; // Page width & height dimension
    private static int LW = 100; private static int LH = 25; // Input field label width and height dimensions
    private static int FW = 250; private static int FH = 25; // Input field width and height dimensions
    private static int BW = 120; private static int BH = 25; // Button width & height dimensions

    private static String pageTitle = "Login ";

    //User input labels
    private JLabel usernameLabel = new JLabel("Username: ");
    private JLabel passwordLabel = new JLabel("Password: ");

    //User input fields
    private JTextField usernameField = new JTextField("");
    private JPasswordField passwordField = new JPasswordField("");

    //Buttons
    private JButton loginButton = new JButton("Login");
    private JButton resetButton = new JButton("Reset");

    public LoginScreenView() {
        this.setTitle(pageTitle);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(PW, PH);

        //Set size of buttons (Width, Height)
        usernameLabel.setPreferredSize(new Dimension(LW, LH));
        usernameField.setPreferredSize(new Dimension(FW, FH));
        passwordLabel.setPreferredSize(new Dimension(LW, LH));
        passwordField.setPreferredSize(new Dimension(FW, FH));
        loginButton.setPreferredSize(new Dimension(BW, BH));
        resetButton.setPreferredSize(new Dimension(BW, BH));

        // Title label panel
        JLabel title = new JLabel(pageTitle);
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        JPanel panelTitle = new JPanel();
        panelTitle.add(title);
        this.getContentPane().add(panelTitle);

        //Username panel
        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        //Password Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(resetButton);

        this.getContentPane().add(usernamePanel);
        this.getContentPane().add(passwordPanel);
        this.getContentPane().add(buttonPanel);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}

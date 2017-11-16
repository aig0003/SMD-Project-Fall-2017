//TODO: Change 'currentUser' to actually be the current user
import javax.swing.*;
import java.awt.*;

public class ProfileScreenView extends JFrame {
    // String Labels/Constants
    private static int PW = 600; private static int PH = 240; // Page width & height dimension
    private static int BW = 180; private static int BH = 25; // Button width & height dimensions
    private static String pageTitle = "Profile ";

    private JButton editPictureButton = new JButton("Change Profile Picture");
    private JButton editPasswordButton = new JButton("Change Password");
    private JButton backButton = new JButton("Back");

    private String imgDir;
    public String getImgDir() { return this.imgDir;}
    public void setImgDir(String imgDir) {this.imgDir = imgDir;}

    private SQLDataAdapter sqlAdapter;

    public ProfileScreenView(SQLDataAdapter sqlDataAdapter){
        this.sqlAdapter = sqlDataAdapter;

        this.setTitle(pageTitle);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(PW, PH);

        //Current user
        String currentUser = "manager";
        Employee employee = sqlDataAdapter.loadEmployee(currentUser);

        //Set size of buttons
        editPasswordButton.setPreferredSize(new Dimension(BW, BH)); //(buttonWidth, buttonHeight)
        editPictureButton.setPreferredSize(new Dimension(BW, BH));
        backButton.setPreferredSize(new Dimension(60, BH));

        // Profile Info Panels
        JPanel userNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        String usersUserName = employee.getUsername();
        userNamePanel.add(new JLabel("Username: " + usersUserName, JLabel.LEFT));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        String usersName = employee.getName();
        namePanel.add(new JLabel("Name: " + usersName, JLabel.LEFT));

        // Profile Image Panel
        JPanel profileImgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        //imgDir =  ;//"/Users/Alec_Goodner/Desktop/SMD_Iteration02/test.jpg";
        imgDir = employee.getProfilePicURL();
        ImageIcon icon = new ImageIcon(imgDir);
        Image image = icon.getImage() ;
        Image newImage = image.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon(newImage);
        JLabel imageLabel = new JLabel("", icon, JLabel.LEFT);
        profileImgPanel.add( imageLabel, BorderLayout.CENTER );


        //Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(editPictureButton);
        buttonPanel.add(editPasswordButton);

        this.getContentPane().add(userNamePanel);
        this.getContentPane().add(namePanel);
        this.getContentPane().add(profileImgPanel);
        this.getContentPane().add(profileImgPanel);
        this.getContentPane().add(buttonPanel);

    }
    public JButton getEditProfilePictureButton() { return editPictureButton; }
    public JButton getEditPasswordButton() { return editPasswordButton; }

    public JButton getBack() { return backButton; }
}

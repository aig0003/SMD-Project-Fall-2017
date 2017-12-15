import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfileScreenView extends JFrame {
    // String Labels/Constants
    private static int PW = 600; private static int PH = 240; // Page width & height dimension
    private static int BW = 180; private static int BH = 25; // Button width & height dimensions
    private static String pageTitle = "Profile ";

    private JButton editPictureButton = new JButton("Change Profile Picture");
    private JButton editPasswordButton = new JButton("Change Password");
    private JButton backButton = new JButton("Back");

    private SQLDataAdapter adapter;

    public ProfileScreenView(SQLDataAdapter adapter){
        //Current user
        Employee employee = Application.getCurrentUser();

        if (employee != null) { //Only does the actual work if a user is logged in. Otherwise it creates a placeholder page.
            this.adapter = adapter;

            this.setTitle(pageTitle);
            this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(PW, PH);


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

            JPanel jobTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            int jobTitle = employee.getEmployeeType();
            String jobTitleStr = "";
            if(jobTitle == 1) { jobTitleStr = "Manager";
            } else { jobTitleStr = "Cashier";
            }
            jobTitlePanel.add(new JLabel("Job Title: " + jobTitleStr, JLabel.LEFT));;

            // Profile Image Panel
            JPanel profileImgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

            String imgDir = employee.getProfilePicURL();

            Image image = null;
            try {
                image = ImageIO.read(new File(imgDir));
            } catch (IOException ex) {
                System.out.println("ERROR: Could not find profile image on current computer (be sure that picture is in <images> folder is in project folder), profile picture set to default");
                try {
                    String dir = System.getProperty("user.dir") + "/images/dummy.jpg";
                    employee.setProfilePicURL(dir);

                    image = ImageIO.read(new File(dir));
                } catch (IOException e) {
                    System.out.println("ERROR: Problems handling profile image");
                }
            }
            Image newImage = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(newImage);
            JLabel imageLabel = new JLabel("", icon, JLabel.LEFT);
            profileImgPanel.add(imageLabel, BorderLayout.CENTER);

            //Buttons Panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(backButton);
            buttonPanel.add(editPictureButton);
            buttonPanel.add(editPasswordButton);

            this.getContentPane().add(userNamePanel);
            this.getContentPane().add(namePanel);
            this.getContentPane().add(jobTitlePanel);
            this.getContentPane().add(profileImgPanel);
            this.getContentPane().add(profileImgPanel);
            this.getContentPane().add(buttonPanel);
        }
    }
    public JButton getEditProfilePictureButton() { return editPictureButton; }
    public JButton getEditPasswordButton() { return editPasswordButton; }

    public JButton getBack() { return backButton; }
}

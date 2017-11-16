import javax.activation.MimetypesFileTypeMap;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class ProfileScreenController implements ActionListener {
    private ProfileScreenView view;
    private SQLDataAdapter adapter;

    private String currentUser = "user1";

    public ProfileScreenController(ProfileScreenView view, SQLDataAdapter adapter) {
        this.view = view;
        this.adapter = adapter;

        view.getEditProfilePictureButton().addActionListener(this);
        view.getBack().addActionListener(this);
        view.getEditPasswordButton().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getEditProfilePictureButton()) {
            editPhoto();
        } else if (e.getSource() == view.getBack()) {
            Application.getInstance().getStoreManagementSystemView().setVisible(true);
            Application.getInstance().getProfileScreenView().setVisible(false);
            return;
        } else if (e.getSource() == view.getEditPasswordButton()) {
            editPassword();
        }
    }

    public void editPhoto() {
        String dir;
        System.out.println("Old image path: " + view.getImgDir());
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fc.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            dir = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            String mimetype= new MimetypesFileTypeMap().getContentType(selectedFile);
            String type = mimetype.split("/")[0];
            if(type.equals("image")) {
                System.out.println("MESSAGE: Selected file approved");
                view.setImgDir(dir);
                Employee employee = adapter.loadEmployee(currentUser);
                employee.setProfilePicURL(dir);
                adapter.saveEmployee(employee);
                System.out.println("New image path: " + view.getImgDir());
                JOptionPane.showMessageDialog(null, "MESSAGE: Profile picture has been changed! (Requires restart to take effect)");
            } else {
                JOptionPane.showMessageDialog(null, "ERROR: Selected file is not a .JPEG or .JPG image");
            }
        } else {
            JOptionPane.showMessageDialog(null, "MESSAGE: File selection cancelled");
        }

        return;
    }

    public void editPassword() {
        String oldPassword = JOptionPane.showInputDialog(view, "Please enter your old password");
        Employee employee = adapter.loadEmployee(currentUser);
        if(Objects.equals(oldPassword, employee.getPassword())) {
            String newPassword = JOptionPane.showInputDialog(view, "Please enter your new password");
            String newPassword2 = JOptionPane.showInputDialog(view, "Please verify new password");
            if(Objects.equals(newPassword, newPassword2)) {
                employee.setPassword(newPassword);
                adapter.saveEmployee(employee);
                JOptionPane.showMessageDialog(view, "Password change successful!");
            } else {
                JOptionPane.showMessageDialog(view, "Passwords did not match");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Password did not match");
        }

    }
}

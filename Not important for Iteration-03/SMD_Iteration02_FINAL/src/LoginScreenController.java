import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreenController implements ActionListener {
    private LoginScreenView view;
    private SQLDataAdapter adapter;

    public LoginScreenController(LoginScreenView view, SQLDataAdapter adapter){
        this.view = view;
        this.adapter = adapter;

        view.getLoginButton().addActionListener(this);
        view.getResetButton().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getLoginButton()) {
            login();
            return;
        } else if (e.getSource() == view.getResetButton()) {
            view.getUsernameField().setText("");
            view.getPasswordField().setText("");
            return;
        }
    }

    private void login() {
        String username = view.getUsernameField().getText();
        char[] password = view.getPasswordField().getPassword(); //Stored as a character array for security reasons.
        String convertedPassword = "";
        for (char c : password) {
            convertedPassword += c;
        }

        Employee loggedInUser = adapter.tryLogin(username, convertedPassword);

        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(view, "Invalid Login Credentials.");
        } else {
            //Changes the logged in user.
            Application.setCurrentUser(loggedInUser);

            //Recreates some pages that rely on a logged in user when the user first logs in.
            Application.getInstance().setProfileScreenView(new ProfileScreenView(adapter));
            Application.getInstance().setProfileScreenController(new ProfileScreenController(Application.getInstance().getProfileScreenView(), adapter));
            Application.getInstance().setStoreManagementSystemView(new StoreManagementSystemView());
            Application.getInstance().setStoreManagementSystemController(new StoreManagementSystemController(Application.getInstance().getStoreManagementSystemView(), adapter));

            //Changes the page view.
            Application.getInstance().getStoreManagementSystemView().setVisible(true);
            Application.getInstance().getLoginScreenView().setVisible(false);
        }

    }
}

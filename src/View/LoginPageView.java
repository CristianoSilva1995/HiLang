package View;
import javax.swing.*;
/**
 * Login Page View
 */

public class LoginPageView extends JFrame {
    private JTextField userText;
    private JPanel main;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JButton signUpButton;
    private JPanel navBar;
    private JPanel bottomPanel;
    private JPanel container;
    private JLabel logoLabel;
    private JLabel userIcon;
    private JLabel PasswordIcon;
    private JLabel SignUpIcon;
    private JLabel forgotPassword;

    public LoginPageView() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        add(main);
        pack();
    }
    //method used by the controller to display message
    public void printUsername(String userName) {
        JOptionPane.showMessageDialog(this, "Welcome, " + userName + "!");
    }

    //method to verify if userNameExists, communicates with the modelDAO
    public void printError(String error) {
        JOptionPane.showMessageDialog(this, error + "!");
    }

    public JTextField getUserText() {
        return userText;
    }

    public JPanel getMain() {
        return main;
    }

    public JPasswordField getPasswordText() {
        return passwordText;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JLabel getLogoLabel() {
        return logoLabel;
    }

    public JLabel getUserIcon() {
        return userIcon;
    }

    public JLabel getPasswordIcon() {
        return PasswordIcon;
    }

    public JLabel getSignUpIcon() {
        return SignUpIcon;
    }

    public JLabel getForgotPassword() {
        return forgotPassword;
    }
}
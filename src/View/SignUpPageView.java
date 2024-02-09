package View;
import javax.swing.*;
/**
 * SignUp Page
 */

public class SignUpPageView extends JFrame {
    private JTextField usernameText;
    private JTextField firstnameText;
    private JTextField surnameText;
    private JTextField emailText;
    private JPasswordField passText;
    private JPasswordField conPassText;
    private JRadioButton teacherRadioButton;
    private JRadioButton studentRadioButton;
    private JButton createAccountButton;
    private JPanel main;
    private JLabel usernameLabel;
    private JLabel firstNameText;
    private JLabel surnameLabel;
    private JLabel emailAddressLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JPanel container;
    private JPanel navBar;
    private JLabel logoIcon;
    private JPanel signUpPanel;
    private JPanel formPanel;
    private JLabel personIconUsername;
    private JLabel personIconFirstName;
    private JLabel personIconLastName;
    private JLabel emailIcon;
    private JLabel passwordIcon;

    public JLabel getPasswordIcon2() {
        return passwordIcon2;
    }

    private JLabel passwordIcon2;
    private ButtonGroup typeAccountGroup = new ButtonGroup();

    public SignUpPageView(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        add(main);
        pack();
    }
    //method to verify if userNameExists, communicates with the modelDAO
    public void printMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    public JTextField getUsernameText() {
        return usernameText;
    }

    public JTextField getFirstnameText() {
        return firstnameText;
    }

    public JTextField getSurnameText() {
        return surnameText;
    }

    public JTextField getEmailText() {
        return emailText;
    }

    public JPasswordField getPassText() {
        return passText;
    }

    public JPasswordField getConPassText() {
        return conPassText;
    }

    public JRadioButton getTeacherRadioButton() {
        return teacherRadioButton;
    }

    public JRadioButton getStudentRadioButton() {
        return studentRadioButton;
    }

    public JButton getCreateAccountButton() {
        return createAccountButton;
    }

    public JPanel getMain() {
        return main;
    }

    public JLabel getLogoIcon() {
        return logoIcon;
    }

    public JLabel getPersonIconUsername() {
        return personIconUsername;
    }

    public JLabel getPersonIconFirstName() {
        return personIconFirstName;
    }

    public JLabel getPersonIconLastName() {
        return personIconLastName;
    }

    public JLabel getEmailIcon() {
        return emailIcon;
    }

    public JLabel getPasswordIcon() {
        return passwordIcon;
    }

    public void setTypeAccountGroup(JRadioButton radioButton) {
        this.typeAccountGroup.add(radioButton);
    }
}

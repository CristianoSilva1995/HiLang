package View;

import javax.swing.*;

/**
 * View for Change Password page
 * @author Arkadiusz Nowak
 */

public class ChangePasswordView extends JFrame {
    private JPanel panelMain;
    private JPanel panelTop;
    private JPanel panelMiddle;
    private JLabel lblLogo;
    private JLabel lblPageTitle;
    private JPanel panelMiddleForm;
    private JTextField txtUser;
    private JTextField txtEmail;
    private JLabel lblUserIcon;
    private JLabel lblEmailIcon;
    private JButton btnSubmit;
    private JPasswordField txtPassword;
    private JLabel lblPasswordIcon;
    private JLabel lblUser;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblPasswordHint;
    private JLabel lblLogIn;
    private JLabel lblSignup;
    private JPanel panelBottom;
    private JButton btnHome;
    private JButton btnProgress;
    private JButton btnLearning;
    private JButton btnChats;
    private JOptionPane dialog;

    public ChangePasswordView() {
        // Exit app on window close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add main panel
        add(panelMain);
        // Disallow resizing of the window
        setResizable(false);

        pack();

        // Set password hint (requirements)
        // @TODO FIX: should be based on config
        lblPasswordHint.setText("<html>Password must be:<br>- between 8 and 255 characters long" +
                "<br>- contain at least one lower and upper case letter</html>");
    }

    // Getters and setters for each of the view's properties

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JPanel getPanelTop() {
        return panelTop;
    }

    public void setPanelTop(JPanel panelTop) {
        this.panelTop = panelTop;
    }

    public JPanel getPanelMiddle() {
        return panelMiddle;
    }

    public void setPanelMiddle(JPanel panelMiddle) {
        this.panelMiddle = panelMiddle;
    }

    public JLabel getLblLogo() {
        return lblLogo;
    }

    public void setLblLogo(JLabel lblLogo) {
        this.lblLogo = lblLogo;
    }

    public JLabel getLblPageTitle() {
        return lblPageTitle;
    }

    public void setLblPageTitle(JLabel lblPageTitle) {
        this.lblPageTitle = lblPageTitle;
    }

    public JPanel getPanelMiddleForm() {
        return panelMiddleForm;
    }

    public void setPanelMiddleForm(JPanel panelMiddleForm) {
        this.panelMiddleForm = panelMiddleForm;
    }

    public JTextField getTxtUser() {
        return txtUser;
    }

    public void setTxtUser(JTextField txtUser) {
        this.txtUser = txtUser;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public JLabel getLblUserIcon() {
        return lblUserIcon;
    }

    public void setLblUserIcon(JLabel lblUserIcon) {
        this.lblUserIcon = lblUserIcon;
    }

    public JLabel getLblEmailIcon() {
        return lblEmailIcon;
    }

    public void setLblEmailIcon(JLabel lblEmailIcon) {
        this.lblEmailIcon = lblEmailIcon;
    }

    public JButton getBtnSubmit() {
        return btnSubmit;
    }

    public void setBtnSubmit(JButton btnSubmit) {
        this.btnSubmit = btnSubmit;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public JLabel getLblPasswordIcon() {
        return lblPasswordIcon;
    }

    public void setLblPasswordIcon(JLabel lblPasswordIcon) {
        this.lblPasswordIcon = lblPasswordIcon;
    }

    public JLabel getLblUser() {
        return lblUser;
    }

    public void setLblUser(JLabel lblUser) {
        this.lblUser = lblUser;
    }

    public JLabel getLblEmail() {
        return lblEmail;
    }

    public void setLblEmail(JLabel lblEmail) {
        this.lblEmail = lblEmail;
    }

    public JLabel getLblPassword() {
        return lblPassword;
    }

    public void setLblPassword(JLabel lblPassword) {
        this.lblPassword = lblPassword;
    }

    public JLabel getLblPasswordHint() {
        return lblPasswordHint;
    }

    public void setLblPasswordHint(JLabel lblPasswordHint) {
        this.lblPasswordHint = lblPasswordHint;
    }

    public JPanel getPanelBottom() {
        return panelBottom;
    }

    public void setPanelBottom(JPanel panelBottom) {
        this.panelBottom = panelBottom;
    }

    public JButton getBtnHome() {
        return btnHome;
    }

    public void setBtnHome(JButton btnHome) {
        this.btnHome = btnHome;
    }

    public JButton getBtnProgress() {
        return btnProgress;
    }

    public void setBtnProgress(JButton btnProgress) {
        this.btnProgress = btnProgress;
    }

    public JButton getBtnLearning() {
        return btnLearning;
    }

    public void setBtnLearning(JButton btnLearning) {
        this.btnLearning = btnLearning;
    }

    public JButton getBtnChats() {
        return btnChats;
    }

    public void setBtnChats(JButton btnChats) {
        this.btnChats = btnChats;
    }

    public JOptionPane getDialog() {
        return dialog;
    }

    public void setDialog(JOptionPane dialog) {
        this.dialog = dialog;
    }

    public JLabel getLblLogIn() {
        return lblLogIn;
    }

    public void setLblLogIn(JLabel lblLogIn) {
        this.lblLogIn = lblLogIn;
    }

    public JLabel getLblSignup() {
        return lblSignup;
    }

    public void setLblSignup(JLabel lblSignup) {
        this.lblSignup = lblSignup;
    }
}
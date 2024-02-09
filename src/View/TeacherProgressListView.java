package View;

import config.Config;
import javax.swing.*;
import java.sql.Connection;

/**
 * View for Teacher Progress List page
 * @author Shiyan Bhandari, Arkadiusz Nowak
 */

public class TeacherProgressListView extends JFrame {
    private JPanel panelMain;
    private JPanel panelTop;
    private JLabel lblLogo;
    private JLabel lblPageTitle;
    private JPanel panelMiddle;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JList listSearchResults;
    private JLabel lblSearchHint;
    private JPanel panelBottom;
    private JButton btnHome;
    private JButton btnProgress;
    private JButton btnLearning;
    private JButton btnChats;
    private JOptionPane dialog;
    private Config config;
    private Connection db;

    public TeacherProgressListView() {
        // Disallow resizing of the window
        setResizable(false);
        // Exit app on window close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add main panel
        add(panelMain);

        // Show window
        pack();
        setVisible(true);

        // Setup search hint
        lblSearchHint.setText("Search for students and their progress:");
    }

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

    public JPanel getPanelMiddle() {
        return panelMiddle;
    }

    public void setPanelMiddle(JPanel panelMiddle) {
        this.panelMiddle = panelMiddle;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(JTextField txtSearch) {
        this.txtSearch = txtSearch;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public JList getListSearchResults() {
        return listSearchResults;
    }

    public void setListSearchResults(JList listSearchResults) {
        this.listSearchResults = listSearchResults;
    }

    public JLabel getLblSearchHint() {
        return lblSearchHint;
    }

    public void setLblSearchHint(JLabel lblSearchHint) {
        this.lblSearchHint = lblSearchHint;
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
}
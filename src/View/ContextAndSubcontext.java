package View;

import Database.Database;
import Models.SubContextDAO;
import config.Config;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


// Components being used by the frame.
// These components make the view part for the MVC.
// They are called by the controller in order to be able to present the user with a graphical interface.
public class ContextAndSubcontext extends JFrame {
    private JPanel mainPanel; // This panel is being used for both Contexts and Subcontexts pages.
    private JButton showAllTopicsBtn;
    private JButton homeButton;
    private JButton learningButton;
    private JButton chatsButton;
    private JTextField displayContextJTextBox;
    private JButton showSubtopicBtn;
    private JPanel headerPanel;
    public JLabel contextHeaderJLable;
    private JList jList;
    private JButton progressButton;
    private JLabel logoLabel;
    private JButton createChatBtn;
    private JPanel showSubTopicJPanel;
    private Connection db;
    private DefaultListModel <String> mod;

    // Getters and Setters for the components on the JSwing.
    public JButton getChatPageButton() {
        return createChatBtn;
    }
    public void setChatPageButton(JButton chatPageButton) {
        this.createChatBtn = chatPageButton;
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public JButton getCreateChatBtn() {
        return createChatBtn;
    }

    public void setCreateChatBtn(JButton createChatBtn) {
        this.createChatBtn = createChatBtn;
    }

    public void setHomeButton(JButton homeButton) {
        this.homeButton = homeButton;
    }

    public JButton getShowAllTopicsBtn() {
        return showAllTopicsBtn;
    }

    public void setShowAllTopicsBtn(JButton showAllTopicsBtn) {
        this.showAllTopicsBtn = showAllTopicsBtn;
    }

    public JButton getLearningButton() {
        return learningButton;
    }

    public void setLearningButton(JButton learningButton) {
        this.learningButton = learningButton;
    }

    public JButton getChatsButton() {
        return chatsButton;
    }

    public void setChatsButton(JButton chatsButton) {
        this.chatsButton = chatsButton;
    }

    public JTextField getDisplayContextJTextBox() {
        return displayContextJTextBox;
    }

    public void setDisplayContextJTextBox(JTextField displayContextJTextBox) {
        this.displayContextJTextBox = displayContextJTextBox;
    }

    public JButton getShowSubtopicBtn() {
        return showSubtopicBtn;
    }

    public void setShowSubtopicBtn(JButton showSubtopicBtn) {
        this.showSubtopicBtn = showSubtopicBtn;
    }

    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    public void setHeaderPanel(JPanel headerPanel) {
        this.headerPanel = headerPanel;
    }

    public JLabel getContextHeaderJLable() {
        return contextHeaderJLable;
    }

    public void setContextHeaderJLable(JLabel contextHeaderJLable) {
        this.contextHeaderJLable = contextHeaderJLable;
    }

    public JList getjList() {
        return jList;
    }

    public void setjList(JList jList) {
        this.jList = jList;
    }

    public JButton getProgressButton() {
        return progressButton;
    }

    public void setProgressButton(JButton progressButton) {
        this.progressButton = progressButton;
    }

    public JLabel getLogoLabel() {
        return logoLabel;
    }

    public void setLogoLabel(JLabel logoLabel) {
        this.logoLabel = logoLabel;
    }

    public JPanel getShowSubTopicJPanel() {
        return showSubTopicJPanel;
    }

    public void setShowSubTopicJPanel(JPanel showSubTopicJPanel) {
        this.showSubTopicJPanel = showSubTopicJPanel;
    }

    public Connection getDb() {
        return db;
    }

    public void setDb(Connection db) {
        this.db = db;
    }

    public DefaultListModel<String> getMod() {
        return mod;
    }

    public void setMod(DefaultListModel<String> mod) {
        this.mod = mod;
    }

    public ContextAndSubcontext() {

        Config config = new Config();
        db = Database.getConnection(config.getProperty("db.name"));
        SubContextDAO subContext = new SubContextDAO(db);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// This terminates the program when user clicks the close button
        this.setContentPane(mainPanel);
        setSize(Integer.valueOf(config.getProperty("window.width")),
                Integer.valueOf(config.getProperty("window.height")));
        dispose();
        // Setting the image location for the logo.
        // Setting the size and display of the icon.
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        logoLabel.setIcon(iconLogo);
        setLocationRelativeTo(null);
        this.setVisible(true);


        // The home page loads when you click on the logo.
        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //new Home();
                dispose();
            }
        });
    }

}

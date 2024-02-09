package View;

import Models.Language;

import javax.swing.*;
import java.util.List;
/**
 * Select Language View
 * @author Cristiano Silva
 */

public class SelectLanguageView extends JFrame {
    private JLabel SelectLanguageLabel;
    private JScrollPane LanguagePanel;
    private JButton spanishFlagButton;
    private JLabel DifficultyLevelLabel;
    private JButton continueButton;
    private JPanel MainPanel;
    private JPanel ContainerPanel;
    private JPanel NavBarPanel;
    private JPanel DifficultyPanel;
    private JPanel languageInnerPanel;
    private JLabel logoLabel;
    private JSlider difficultySlider;
    private JTextArea informationAreaLanguage;
    private Integer getDifficultyID;
    private String getLanguage;

    private List<Language> languageList;

    public SelectLanguageView() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Add main panel
        add(MainPanel);
        // Disallow resizing of the window
        setResizable(false);
        pack();
    }
    //Retrieving messages from the controller and displaying it on the view
    public void printMessage(String message){
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f, message);
    }

    //Bellow is the getters to get the element on the SelectLanguageController
    public JButton getContinueButton() {
        return continueButton;
    }

    public JPanel getLanguageInnerPanel() {
        return languageInnerPanel;
    }

    public JLabel getLogoLabel() {
        return logoLabel;
    }

    public JSlider getDifficultySlider() {
        return difficultySlider;
    }

}


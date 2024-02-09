package View;
/* AUTHOR: Jonathan Carrillo-Sanchez
 *  ID: w1758229 */

import javax.swing.*;

public class ChatPage extends JFrame {
    private JPanel chat_frame_panel;
    private JButton homeButton;
    private JButton learningButton;
    private JButton chatsButton;
    private JButton progressButton;
    private JLabel difficultyLevel_lb;
    private JLabel chatContext_lb;
    private JPanel nav_bar;
    private JPanel top_bar;
    private JLabel logoImage;
    private JTextArea textArea1;
    private JTextArea person1_ta;
    private JTextArea person2_ta;
    private JButton nextDialogueBtn;
    private int idConversation;
    // Variables below are needed to be accessed by other classes, via getter methods.
    private int userId;
    private int idSubcontext;
    private int idLanguage;
    private String difficulty;

    // Constructor takes in values that will become "view instance variables" that will be retrieved from the controller
    // for ease of access.
    // default constructor
    public ChatPage(int _userId, int _idSubcontext, int _idLanguage, String _difficulty) {
        this.userId = _userId;
        this.idSubcontext = _idSubcontext;
        this.idLanguage = _idLanguage;
        this.difficulty = _difficulty;

        // Add main panel
        add(chat_frame_panel);
        // Disallow resizing of the window
        setResizable(false);
        pack();
        // Center window
        setLocationRelativeTo(null);

    }

    public JPanel getChat_frame_panel() {
        return chat_frame_panel;
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public JButton getLearningButton() {
        return learningButton;
    }

    public JButton getChatsButton() {
        return chatsButton;
    }

    public JButton getProgressButton() {
        return progressButton;
    }

    public JLabel getDifficultyLevel_lb() {
        return difficultyLevel_lb;
    }

    public JLabel getChatContext_lb() {
        return chatContext_lb;
    }

    public JPanel getNav_bar() {
        return nav_bar;
    }

    public JPanel getTop_bar() {
        return top_bar;
    }

    public JLabel getLogoImage() {
        return logoImage;
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public JTextArea getPerson1_ta() {
        return person1_ta;
    }

    public JTextArea getPerson2_ta() {
        return person2_ta;
    }

    public JButton getNextDialogueBtn() {
        return nextDialogueBtn;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public int getUserId() {
        return userId;
    }

    public int getIdSubcontext() {
        return idSubcontext;
    }

    public int getIdLanguage() {
        return idLanguage;
    }

    public String getDifficulty() {
        return difficulty;
    }
}

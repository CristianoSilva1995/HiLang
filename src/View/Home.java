package View;
/* AUTHOR: Jonathan Carrillo-Sanchez
*  ID: w1758229 */

import javax.swing.*;

public class Home extends JFrame {
    private JPanel home_frame_panel;
    private JButton changeLanguageBtn;
    private JButton homeButton;
    private JButton progressButton;
    private JButton learningButton;
    private JButton chatsButton;
    private JScrollPane main_content_sp;
    private JScrollPane container_sp;
    private JPanel content_panel;
    private JLabel suggestion_lb1;
    private JLabel suggestion_lb2;
    private JLabel suggestion_lb3;
    private JLabel suggestion_lb4;
    private JLabel suggestion_lb5;
    private JLabel suggestion_lb6;
    private JLabel teacher_student_lb;
    private JPanel nav_bar;
    private JPanel top_bar;
    private JComboBox difficultyLevels_cb;
    private JLabel difficulty_level_display;
    private JLabel logo;
    private JLabel flag_image;
    private JButton changeDifficulty_btn;

    private Integer userId;
    private Integer languageId;
    private String difficultyLevel;

    // created getters for these main variables that I will need to retrieve to display on the Home view
    public Integer getUserId() {
        return userId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public JButton getChangeDifficulty_btn() {
        return changeDifficulty_btn;
    }

    public String getDifficulty() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    // Constructor takes in values that will become "view instance variables" that will be retrieved from the controller
    // for ease of access.
    // default constructor
    public Home(Integer userId,Integer languageId, String difficultyLevel) {
        this.userId = userId;
        this.languageId = languageId;
        this.difficultyLevel = difficultyLevel;

        // Add main panel
        add(home_frame_panel);
        // Disallow resizing of the window
        setResizable(false);
        pack();
        // Center window
        setLocationRelativeTo(null);

    }

    // Below are the getters to get the element on the HomeController
    public JPanel getHome_frame_panel() {
        return home_frame_panel;
    }

    public JButton getChangeLanguageBtn() {
        return changeLanguageBtn;
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public JButton getProgressButton() {
        return progressButton;
    }

    public JButton getLearningButton() {
        return learningButton;
    }

    public JButton getChatsButton() {
        return chatsButton;
    }

    public JScrollPane getMain_content_sp() {
        return main_content_sp;
    }

    public JScrollPane getContainer_sp() {
        return container_sp;
    }

    public JPanel getContent_panel() {
        return content_panel;
    }

    public JLabel getSuggestion_lb1() {
        return suggestion_lb1;
    }

    public JLabel getSuggestion_lb2() {
        return suggestion_lb2;
    }

    public JLabel getSuggestion_lb3() {
        return suggestion_lb3;
    }

    public JLabel getSuggestion_lb4() {
        return suggestion_lb4;
    }

    public JLabel getSuggestion_lb5() {
        return suggestion_lb5;
    }

    public JLabel getSuggestion_lb6() {
        return suggestion_lb6;
    }

    public JLabel getTeacher_student_lb() {
        return teacher_student_lb;
    }

    public JPanel getNav_bar() {
        return nav_bar;
    }

    public JPanel getTop_bar() {
        return top_bar;
    }

    public JComboBox getDifficultyLevels_cb() {
        return difficultyLevels_cb;
    }

    public JLabel getDifficulty_level_display() {
        return difficulty_level_display;
    }

    public JLabel getLogo() {
        return logo;
    }

    public JLabel getFlag_image() {
        return flag_image;
    }
}

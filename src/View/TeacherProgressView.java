package View;
import Models.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Teacher Progress View
 * @author Cristiano Silva
 */

public class TeacherProgressView extends JFrame {
    private JPanel MainPanel;
    private JLabel studentName;
    private JPanel studentProgressPanel;
    private JLabel StudentProgressLabel;
    private JProgressBar StudentProgressBar;
    private JButton contextIMG;
    private JPanel ContainerPanel;
    private JLabel logoLabel;
    private JList listInformationDisplay;
    private JPanel navBar;
    private JTextArea displayInfo;
    private JLabel displayInfoLabel;
    private List<Context> contextArrayList = new ArrayList<>();

    private DefaultListModel listModel;

    public Integer getUserId() {
        return userId;
    }

    private Integer userId;
    public TeacherProgressView(Integer userID) throws Exception {
        this.userId = userID;
        add(MainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
    }

    //Bellow is all the getters used on the controller
    public JLabel getStudentName() {
        return studentName;
    }

    public JProgressBar getStudentProgressBar() {
        return StudentProgressBar;
    }

    public JLabel getLogoLabel() {
        return logoLabel;
    }

    public JList getListInformationDisplay() {
        return listInformationDisplay;
    }

    public JTextArea getDisplayInfo() {
        return displayInfo;
    }

    public JLabel getDisplayInfoLabel() {
        return displayInfoLabel;
    }
}

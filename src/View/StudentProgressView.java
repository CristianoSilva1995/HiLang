package View;

import Database.Database;
import Models.Context;
import Models.TeacherProgressDAO;
import config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/*
 * @Author Oliver, Cristiano
 */
public class StudentProgressView extends JFrame {
    private Integer userId;
    private JPanel MainPanel;
    private JLabel studentName;
    private JPanel NavBarPanel;
    private JPanel studentProgressPanel;
    private JLabel StudentProgressLabel;
    private JProgressBar StudentProgressBar;
    private JPanel ContainerPanel;
    private JLabel logoLabel;

    private Connection db;
    private List<Context> contextArrayList = new ArrayList<>();
    private DefaultListModel listModel;
    private JList listTopicDisplay;
    private JTextArea displayInfo;
    private JPanel panelInfoArea;
    private JLabel displayInfoLabel;

    public Integer getUserId() {
        return userId;
    }

    public StudentProgressView(Integer userID) throws Exception {
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
        return listTopicDisplay;
    }

    public JTextArea getDisplayInfo() {
        return displayInfo;
    }

    public JLabel getDisplayInfoLabel() {
        return displayInfoLabel;
    }


    private void populateList() {
        for (int i = 0; i < contextArrayList.size(); i++) {
            listModel.addElement(contextArrayList.get(i).getContextName());
        }
    }
}

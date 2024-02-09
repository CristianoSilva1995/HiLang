package Controllers;

import Models.SubContext;
import Models.TeacherProgress;
import View.TeacherProgressView;
import config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.util.List;
/**
 * TeacherProgressController
 * @author Cristiano Silva
 */
public class TeacherProgressController {
    private AppController appController;
    private TeacherProgressView view;
    private TeacherProgress model;
    private Connection db;
    private Config config;

    private List<SubContext> subContextArrayList;
    private DefaultListModel listModel;

    //Retrieves from the appController the view, model use and the AppController
    //Defines config
    //sets the Connection to the DB
    public TeacherProgressController(TeacherProgressView view, TeacherProgress model, AppController appController) {
        this.view = view;
        this.model = model;
        this.appController = appController;
        this.config = appController.getConfig();
        this.db = appController.getDb();
        this.model.setDb(db);
        initView();
        initController();
    }


    //Initializes the TeacherProgress view
    public void initView() {
        listModel = new DefaultListModel();
        view.setTitle(config.getProperty("pages.TeacherProgress.title"));
        setViewSize();
        view.setLocationRelativeTo(null);
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images") + "/logo.jpeg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getLogoLabel().setIcon(iconLogo);

        view.getListInformationDisplay().setModel(listModel);

        //retrieves a list of subContext from the model
        subContextArrayList = model.getAllSubContextFromConversationHistory(view.getUserId());
        //retrieves the student name from the model
        view.getStudentName().setText(model.getUsername(view.getUserId()));
        //setting the view size
        //adding the subContext names to the JList
        populateList();
        view.getDisplayInfoLabel().setText("Click on topic to display more");
        //retrieving the percentage of topics done by the user
        Integer getPercentageProgressBar = getPercentage(model.getCountTotalCompletedSubContext(view.getUserId()), model.getCountTotalSubContext());
        //setting the percentage inside the progress bar
        view.getStudentProgressBar().setValue(getPercentageProgressBar);
        view.getStudentProgressBar().setStringPainted(true);
        view.setVisible(true);
    }

    //Initializes controller
    //Adds an ListSelectionListener to retrieve the clicked subContext
    public void initController(){
        view.getListInformationDisplay().addListSelectionListener(e -> displayInfo());
        view.getLogoLabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            //Navigate to HomePage through the appController, which calls the HomePage
            //disposes the current view
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispose();
                appController.navigateToHomePage();
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });
    }

    //Retrieves the information to be displayed from the model
    private void displayInfo(){
        view.getDisplayInfo().setLineWrap(true);
        view.getDisplayInfo().setWrapStyleWord(true);
        view.getDisplayInfoLabel().setText("Information regarding student ");
        String getSelectedSubContext = view.getListInformationDisplay().getSelectedValue().toString();
        view.getDisplayInfo().setText("Context: " + model.getContextNameDB(getSelectedSubContext));
        view.getDisplayInfo().append("\nSub Context: " + getSelectedSubContext);
        view.getDisplayInfo().append("\nDifficulty: " + model.getDifficultyDB(getSelectedSubContext));
        Integer idConversation = model.getIdConversation(view.getUserId(), model.getSubContextID(getSelectedSubContext));
        view.getDisplayInfo().append("\nTimes Accessed: " + model.getTimesAccessedAndCompleted(view.getUserId(),idConversation).getTimesAccessed());
        view.getDisplayInfo().append("\nCompleted: " + model.getTimesAccessedAndCompleted(view.getUserId(),idConversation).getCompleted());
    }

    //sets the size of the view
    private void setViewSize() {
        view.setPreferredSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMinimumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMaximumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
    }

    //Retrieves the subtopic percentage achieved that the user has completed
    private Integer getPercentage(double size1, double size2) {
        return (int) ((size1 / (size2)) * 100);
    }

    //Populates the listModel with the list of subContexts
    private void populateList(){
        for(int i = 0; i < subContextArrayList.size(); i++){
            listModel.addElement(subContextArrayList.get(i).getSubContextName());
        }
    }
}

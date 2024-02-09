package Controllers;

import Models.*;
import View.*;
import config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
/* AUTHOR: Jonathan Carrillo-Sanchez
 *  ID: w1758229 */
public class HomeController implements ActionListener {

    private AppController appController;
    private HomeModel model;
    private Home view;
    private Connection db;
    private Config config;
    // Constructor initialises with the main App Controller, Home data model and Home view page.
    // default constructor
    public HomeController(AppController app, HomeModel model, Home view) {
        this.appController = app;
        this.model = model;
        this.view = view;
        this.config = appController.getConfig();
        this.db = appController.getDb();
        this.model.setDb(db);
        initView();
        initListeners();

    }

    // Create the frame , from using the Home view. Size settings, population of combo boxes and cosmetics are set.
    public void initView() {
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.setTitle(config.getProperty("pages.Home.title"));
        setViewSize();

        // Setting appropriate JLabels with data, such as difficulty, User type, language selected.
        setLabels();

        // Disallow resizing of the window
        view.setResizable(false);
        // Center window
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    // For the JLabel suggestions on the Home View.
    public void initListeners() {

        // All these labels are hard-coded with the sub-context because they are previously set by the developer for the
        // User to easily start a new chat right after they log in.
        view.getSuggestion_lb1().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                appController.navigateToChatPageFromJLabel(12);
                view.dispose();
            }
        });
        view.getSuggestion_lb2().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                appController.navigateToChatPageFromJLabel(25);
//                new ChatPage(1,25,languageId,"A2");
                view.dispose();
            }
        });
        view.getSuggestion_lb3().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                appController.navigateToChatPageFromJLabel(16);
                view.dispose();
            }
        });
        view.getSuggestion_lb4().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                appController.navigateToChatPageFromJLabel(49);
                view.dispose();
            }
        });
        view.getSuggestion_lb5().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                appController.navigateToChatPageFromJLabel(22);
                view.dispose();
            }
        });
        view.getSuggestion_lb6().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                appController.navigateToChatPageFromJLabel(1);
                view.dispose();
            }
        });
        // Initialising on-click listeners for the navigation bar buttons and top panel elements such as combo box and
        // change language button.
        view.getHomeButton().addActionListener(this);
        view.getLearningButton().addActionListener(this);
        view.getChatsButton().addActionListener(this);
        view.getProgressButton().addActionListener(this);
        view.getChangeLanguageBtn().addActionListener(this);
        view.getChangeDifficulty_btn().addActionListener(this);
//        view.getDifficultyLevels_cb().addActionListener(this);
    }



    // This method will run inside the constructor of the Home class to initialise the labels with correct values
    public void setLabels() {
        view.getDifficulty_level_display().setText(String.valueOf(view.getDifficulty()));
        view.getFlag_image().setText(model.getLanguageNameDB(view.getLanguageId()));
        if (getUserType() == 0) view.getTeacher_student_lb().setText("Student");
        else view.getTeacher_student_lb().setText("Teacher");

        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getLogo().setIcon(iconLogo);
    }

    // method retrieves a string to check whether the user is of type Student of Teacher
    // or maybe this is not needed in this class as another class may take care of that
    public Integer getUserType() {
        return model.getUserTypeDB(view.getUserId());
    }

    private void setViewSize() {
        view.setPreferredSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMinimumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMaximumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
    }
    // logic for buttons, navigate to pages using app controller
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getHomeButton()) {
            appController.navigateToHomePage();
            this.view.dispose();
        } else if (e.getSource() == view.getLearningButton()) {
            appController.navigateToContextAndSubcontext();
            this.view.dispose();
        } else if (e.getSource() == view.getChatsButton()) {
            appController.navigateToChatHistory(view.getUserId());
            this.view.dispose();
        } else if (e.getSource() == view.getProgressButton()) {
            if (getUserType() == 0) {
                appController.navigateToStudentProgress();
                this.view.dispose();
            } else {
                appController.navigateToTeacherProgress(view.getUserId());
                this.view.dispose();
            }
            view.dispose();
        } else if (e.getSource() == view.getChangeLanguageBtn() || e.getSource() == view.getChangeDifficulty_btn()) {
            if (getUserType() == 0) { // If the user is a student, they are able to navigate back to the select language page.
                appController.navigateSelectLanguage();
                view.dispose();
            } // otherwise, if their usertype is something other than 0 (1 most likely, a teacher) then simply nothing happens.
        }
    }
}

package Controllers;

import Models.TeacherProgressList;
import View.TeacherProgressListView;
import config.Config;
import utils.Helper;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Objects;

/**
 * Controller for Teacher Progress List page
 * @author Arkadiusz Nowak
 */

public class TeacherProgressListController {
    private AppController app;
    private TeacherProgressList model;
    private TeacherProgressListView view;
    private Connection db;
    private Config config;

    public TeacherProgressListController(AppController app, TeacherProgressList model, TeacherProgressListView view) {
        // Store parameters if not null, otherwise throw null pointer exception
        this.app = Objects.requireNonNull(app);
        this.model = Objects.requireNonNull(model);
        this.view = Objects.requireNonNull(view);

        // Get app config
        this.config = app.getConfig();

        // Get Db link
        this.db = app.getDb();

        // Pass db connection to the model
        this.model = model;
        this.model.setDb(db);

        // Initialize and show view
        initView();
        initController();

        // Restrict access to teachers
        if (!app.isUserTeacher()) {
            view.getDialog().showMessageDialog(view, "Access denied! You need to be a teacher.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            app.navigateToHomePage();
        }
    }

    public void initView() {
        // Set title
        view.setTitle(config.getProperty("pages.TeacherProgressList.title"));

        // Set window size
        setViewSize();
        // Center window
        view.setLocationRelativeTo(null);

        // Setup icons
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").
                getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getLblLogo().setIcon(iconLogo);

        // Show frame
        view.setVisible(true);
    }

    private void setViewSize() {
        view.setPreferredSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMinimumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMaximumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
    }

    public void initController() {
        view.getBtnSearch().addActionListener(e -> processSearch());
        view.getListSearchResults().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String user = view.getListSearchResults().getSelectedValue().toString();
                Integer idUser = Helper.getKeyIdByValue(model.getSearchResults(), user);
                //JOptionPane.showMessageDialog(view, "Showing data for user [" + user + "]");
                // Dispose current view
                view.dispose();
                app.navigateToTeacherProgress(idUser);
            }
        });
        // Navigation bar buttons
        // Navigates user to Home page
        view.getBtnHome().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                app.navigateToHomePage();
            }
        });
        // Navigates user to Select Language page
        view.getBtnLearning().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                app.navigateToContextAndSubcontext();
            }
        });
        // Navigates user to Chat History
        view.getBtnChats().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                app.navigateToChatHistory(app.getCurrentIdUser());
            }
        });
        // Navigates user to their Progress Page
        // or, if Teacher, to student search
        view.getBtnProgress().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                // Navigate to different page based on user type
                if (app.isUserTeacher()) {
                    app.navigateToTeacherProgressList();
                } else {
                    app.navigateToStudentProgress();
                }
            }
        });
    }

    private void processSearch() {
        saveFormDataToModel();
        String error;
        // @TODO move logic below to model
        error = model.verifyUserInput();
        if (error.isEmpty()) {
            error = model.findUser();
            // If there's no error, proceed
            if (error.isEmpty()) {
                // GET SEARCH RESULTS FROM MODEL & DISPLAY THEM IN THE VIEW
                view.getListSearchResults().setModel(model.getListModel());

            } else { // If model returned error, display it
                view.getDialog().showMessageDialog(view, error,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            view.getDialog().showMessageDialog(view, error,
                    "Error", JOptionPane.ERROR_MESSAGE);
            error = "";
        }
    }

    private void saveFormDataToModel() {
        model.setSearch(view.getTxtSearch().getText());
    }
}
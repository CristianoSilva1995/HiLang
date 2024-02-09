package Controllers;

import Models.ChatHistory;
import View.ChatHistoryView;
import config.Config;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Objects;

/**
 * Controller for Chat History page
 * @author Shiyan Bhandari, Arkadiusz Nowak
 */

public class ChatHistoryController {
    private AppController app;
    private ChatHistory model;
    private ChatHistoryView view;
    private Connection db;
    private Config config;

    public ChatHistoryController(AppController app, ChatHistory model, ChatHistoryView view) {
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
    }

    public void initView() {
        // Set title
        view.setTitle(config.getProperty("pages.ChatHistory.title"));

        // Set window size
        setViewSize();
        // Center window
        view.setLocationRelativeTo(null);

        // Setup logo icon
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").
                getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getImgFlag().setIcon(iconLogo);

        // Show frame
        view.setVisible(true);
    }

    public void initController() {
        // GET SEARCH RESULTS FROM MODEL & DISPLAY THEM IN THE VIEW
        // If the Teacher is accessing the page it will show previously selected User's Chat History,
        // otherwise, if current user is a student, it will show currently logged in User's Chat History
        if (app.getCurrentAccountType() == 1 && !Objects.equals(model.getIdUser(), app.getCurrentIdUser())) {
            view.getLblSubHeader().setText(model.getStudentName()+"'s list of chats");
            model.findChatsByUserId(model.getIdUser());
        } else {
            view.getLblSubHeader().setText("List of your chats");
            model.findChatsByUserId(app.getCurrentIdUser());
        }

        view.getChatList().setModel(model.getListModel());

        // Navigate to Teacher View Chats page with user id as parameter
        view.getChatList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // "["+view.getChatList().getSelectedIndex()+ " -> "+ model.getConversationsList().get(view.getChatList().getSelectedIndex())+"]"
                view.getDialog().showMessageDialog(view,view.getChatList().getSelectedValue());
                view.dispose();
                if (app.isUserTeacher()) {
                    int idConversation = model.getConversationsList().get(view.getChatList().getSelectedIndex());
                    app.navigateToTeacherViewChats(idConversation);
                } else {
                    app.navigateToChatPage(model.getSubcontextsList().get(view.getChatList().getSelectedIndex()));
                    //app.navigateToChatPage(app.getCurrentIdUser());
                }
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

    private void setViewSize() {
        view.setPreferredSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMinimumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMaximumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
    }
}
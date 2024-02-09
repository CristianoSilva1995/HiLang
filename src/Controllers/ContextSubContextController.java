
package Controllers;

import Models.Context;
import Models.SubContext;
import View.ContextAndSubcontext;
import config.Config;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

    public class ContextSubContextController {
    private AppController app;
    private SubContext model;
    private ContextAndSubcontext view;
    private Connection db;
    private Config config;
    private List <Context> contextList;
    private List <SubContext> subContextList;
    private DefaultListModel defaultListModel;

    // This controller helps with the following.
    // 1. Fetches data from user.
    // 2. Presents it to the SubContext Model.
    // 3. Gets the result back from the model to the ContextSubContextController .
    // 4. ContextSubContextController submits the results of the view.
    // 5. The ContextSubContext  view renders to the controller the information in a way that is understandable to the user. -In this case using the GUI components.
    // 6. The controller renders the information back to the user and the cycle continues.
    public ContextSubContextController(SubContext model, ContextAndSubcontext view, AppController app) {
        this.app = app;
        this.model = model;
        this.view = view;

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
        view.setTitle(config.getProperty("pages.Context.title"));

        // Set window size
        view.getMainPanel().setPreferredSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));

        // Setup icons
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").
                getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getLogoLabel().setIcon(iconLogo);

        defaultListModel = new DefaultListModel();
        view.getjList().setModel(defaultListModel);
       // contextList= model.getContextList();

        // Show frame
        getContextsList();
        view.setVisible(true);

    }

    public void initController() {
          // uses the view to get the getShowSubtopicBtn.
          // on mouse click, we are calling the local method getSubcontextListByName.
          view.getShowSubtopicBtn().addActionListener(e -> getSubcontextListByName((String)view.getjList().getSelectedValue()));

          // uses the view to get the getShowAllTopicsBtn.
          // on mouse click, we are calling the local method getContextsList.
          view.getShowAllTopicsBtn().addActionListener(e -> getContextsList());

          // uses the view to get the getShowAllTopicsBtn.
          // on mouse click, we are calling the local method getContextsList.
          view.getCreateChatBtn().addActionListener(e -> getSubContextId( (String)view.getjList().getSelectedValue()));

          // Home Button: - Navigates user to Home page
          view.getHomeButton().addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  view.dispose();
                  // @TODO modify after Home Page gets fixed
                  app.navigateToHomePage();
              }
          });

         // Learning Button: - Navigates user to context and subcontext page


          view.getLearningButton().addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  view.dispose();
                  app.navigateToContextAndSubcontext();

              }
          });

        // Chat Button: - Navigates user to chat history page.
          view.getChatsButton().addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  view.dispose();
                  app.navigateToChatHistory(app.getCurrentIdUser());
              }
          });

        // Progress Button: - Navigates user to student progress.
          view.getProgressButton().addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  if (app.getCurrentAccountType() == 1) {
                      app.navigateToTeacherProgressList();
                  } else {
                      app.navigateToStudentProgress();
                  }
              }
          });
    }
    // getContextsList method that calls getContextName() methods from the SubContext model.
    // It renders the result to a jList contained in the Frame - on ContextSubContext view.
    private void getContextsList() {
        // This hides and disables the createChatBtn as it only relates to the subcontext only and not context
        // we are hiding from user to prevent user from clicking while on this page.
        // view.getCreateChatBtn().disable();
        // view.getCreateChatBtn().hide();

        view.getContextHeaderJLable().setText("CONTEXT/TOPIC PAGE");
        String value = (String)view.getjList().getSelectedValue();
        view.getDisplayContextJTextBox().setText(value);

        //defaultListModel.clear();
        contextList = model.getContextList();

        int contextListSize = contextList.size();
        for (int i = 0; i < contextListSize; i++) {
            defaultListModel.addElement(contextList.get(i).getContextName());

        }

    }

        // getSubcontextListByName method that calls getSubContextId(name) and getSubContextListByID(getSubContextId) methods from the SubContext model
        // It renders the result to a jList contained in the Frame - on ContextSubContext view.
    private void getSubcontextListByName(String name) {
        // This shows and enables the createChatBtn so that when this button is clicked,
        // it will pass the id of the selected context to the chat page.
        // view.getCreateChatBtn().enable();
        // view.getCreateChatBtn().show();

        Frame frame = new Frame();
        String value = (String)view.getjList().getSelectedValue();
        view.getDisplayContextJTextBox().setText(value);

        defaultListModel.clear();

        int getSubContextId = model.getContextId(name);
        subContextList = model.getSubContextListByID(getSubContextId);


        if (name != null) {
            for (int i = 0; i < subContextList.size(); i++) {
                defaultListModel.addElement(subContextList.get(i).getSubContextName());
                view.getContextHeaderJLable().setText("SUB-CONTEXT PAGE");
            }

        }
        else {
            JOptionPane.showMessageDialog(frame,"You must select a Topic!");
            view.getjList();
            getContextsList();
        }

    }


        // getSubContextId method that calls getSubContextId(name) method from the SubContext model
        // It navigates to chat page on mouse click.
    public void getSubContextId(String name) {
        Frame frame = new Frame();
        int getSubContextId = model.getSubContextId(name);

        // This code prevents the user from creating a chat using a topic. The user must use a subtopic to proceed to create chat.
        if (getSubContextId == 0) {
            JOptionPane.showMessageDialog(frame, "Please chose a SubTopic to Create Chat!!");
        }
        else {
            view.dispose();
            app.navigateToChatPage(getSubContextId);
        }


        }

    }




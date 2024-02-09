package Controllers;
/* AUTHOR: Jonathan Carrillo-Sanchez
 *  ID: w1758229 */
import Models.*;
import View.ChatPage;
import View.ContextAndSubcontext;
import config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ChatPageController implements ActionListener {
    private AppController appController;
    private ChatPageModel model;
    private ChatPage view;
    private Config config;
    private Connection db;

    // ChatPage Data structures
    private ArrayList<String> person1_dialogue = new ArrayList<>();
    private ArrayList<String> person2_dialogue = new ArrayList<>();
    private List<Speech> messages = new ArrayList<Speech>();
    private int counter = 0;
    private Boolean hasCompleted;
    // Constructor initialises with the main App Controller, ChatPage data model and ChatPage view.
    // default constructor
    public ChatPageController(AppController app,ChatPageModel model, ChatPage view) {
        this.appController = app;
        this.model = model;
        this.view = view;
        this.config = appController.getConfig();
        this.db = appController.getDb();
        this.model.setDb(db);
        initView();
        // Checks to see if a teacher is attempting to open up a chat, if true then show message and navigate back to home.
        if (model.getUserDAO().getUserTypeById(this.view.getUserId()) == 1) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "You are a teacher, you cannot complete a chat!");
            appController.navigateToHomePage();
            this.view.dispose();
        }
    }
    // Setting up view styling , logo and config with frame size as well as action listeners for buttons.
    private void initView() {
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.setTitle(config.getProperty("pages.ChatPage.title"));
        setViewSize();
        // setting logo image to 100x100 on Home screen
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images") + "/logo.jpeg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getLogoImage().setIcon(iconLogo);
        // Initialising text format for text areas so that it wraps inside the text boxes.
        view.getPerson1_ta().setLineWrap(true);
        view.getPerson1_ta().setWrapStyleWord(true);
        view.getPerson2_ta().setLineWrap(true);
        view.getPerson2_ta().setWrapStyleWord(true);
        // Initialising action listeners for the navigation bar buttons
        view.getHomeButton().addActionListener(this);
        view.getLearningButton().addActionListener(this);
        view.getChatsButton().addActionListener(this);
        view.getProgressButton().addActionListener(this);
        // label of the difficulty of the current chat.
        view.getDifficultyLevel_lb().setText(view.getDifficulty());
        displayDialogue();
        view.setResizable(false);
        // Center window
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    // using the two arraylists for person 1 and person 2, iterate through the array and set the text of the text area 1
    // and text area 2
    public void displayDialogue() {

        view.getChatContext_lb().setText(model.getSubContextDAO().getSubContextName(view.getIdSubcontext()));
        // pass in global class values that were passed in through class instructor (given from last frame, sub-context view)
        Integer idConversation = model.getConversationDAO().getConversationId(view.getIdSubcontext(), view.getIdLanguage(), view.getDifficulty());
        messages = model.getSpeechDAO().getMessagesByConversationId(idConversation);
        System.out.println("id conversaton: " +idConversation);

        // Populating ArrayLists with appropriate dialogue for person 1 and person 2
        for (Speech speech_object : messages) {
            if (speech_object.getPerson() == 'A') {
                person1_dialogue.add(speech_object.getMessage());
            } else {
                person2_dialogue.add(speech_object.getMessage());
            }
        }

        // Checks to add "buffers" to smaller dialogue lists that would otherwise throw an OutOfBounds error when iterating through
        if (person1_dialogue.size() != person2_dialogue.size()) {
            int difference = Math.abs(Math.max(person1_dialogue.size(),person2_dialogue.size())
                    - Math.min(person1_dialogue.size(),person2_dialogue.size()));
            if (person1_dialogue.size() < person2_dialogue.size()) {
                for (int i = 0; i < difference;i++) person1_dialogue.add("None");
            } else {
                for (int i = 0; i < difference;i++) person2_dialogue.add("None");
            }
        }
        // Action listener to display next dialogue lines for Person A and Person B
            view.getNextDialogueBtn().addActionListener(e -> {
//                 If no Speech objects were stored in messages, i.e., nothing was retrieved by the query because the difficulty
//                 did not match, simply display that the user cannot play this chat.
                if (messages.isEmpty()) {
                    JFrame f = new JFrame();
                    JOptionPane.showMessageDialog(f, "You cannot play this chat, your difficulty level does not match!");
                    appController.navigateToHomePage();
                    this.view.dispose();
                } else {
                    int maxLines = Math.max(person1_dialogue.size(),person2_dialogue.size());
                    if (counter < maxLines) {
                        //person1's dialogue
                        view.getPerson1_ta().append("- Line " + (counter+1) + "-\n");
                        view.getPerson1_ta().append(person1_dialogue.get(counter));
                        view.getPerson1_ta().append("\n\n");
                        // person2's dialogue
                        view.getPerson2_ta().append("- Line " + (counter+1) + "-\n");
                        view.getPerson2_ta().append(person2_dialogue.get(counter));
                        view.getPerson2_ta().append("\n\n");
                        counter++;
                    } else {
                        JFrame f = new JFrame();
                        JOptionPane.showMessageDialog(f, "You have finished this chat!");
                        hasCompleted = true;
                    }
                }



//            if (hasCompleted) model.getConversationHistoryDAO().changeToCompleted(view.getUserId(),idConversation);
            });

    }
    /* This method does was supposed to create a new update on the conversationHistory table, however, I ran into issues
    *  where the SQLite DB would get "locked", perhaps due to too many DB connections, however, I ensured that I passed the
    *  same DB connection, without creating a new one, so this error stopped me from fully implementing this feature of the
    *  app. However, the user can still go through a chat as normal, it just won't be saved in this current
    *  version.*/
//    public void addToDB(Integer convId) {
//        ConversationHistory conversationHistory_object = model.getConversationHistoryDAO()
//                .getTimesAccessedAndCompleted(view.getUserId(),convId);
//        if (model.getConversationHistoryDAO().getHasOpenedBefore(view.getUserId(),convId)){
//            model.getConversationHistoryDAO()
//                    .changeTimesAccessed(view.getUserId(),convId,conversationHistory_object.getTimesAccessed());
//        } else {
//            // If the user has not opened this conversation before, create a new conversation history record in DB
//            // Check to see whether there are any pre-existing conversation history records for this particular conversation
//            int newestConversationHistoryId = (model.getConversationHistoryDAO()
//                    .getConversationHistoryById(convId).isEmpty()) ? 1 :
//                    model.getConversationHistoryDAO().getMaxConversationHistoryId() + 1;
//
//            model.getConversationHistoryDAO()
//                    .createNewConversationHistory(newestConversationHistoryId,view.getUserId(),view.getUserId()+1,convId,
//                    1,false);
//        }
//    }

    private void setViewSize() {
        view.setPreferredSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMinimumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMaximumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getHomeButton()) {
            appController.navigateToHomePage();
//            Home view = new Home(userId,idLanguage,difficulty);
            view.dispose();
        } else if (e.getSource() == view.getLearningButton()) {
            ContextAndSubcontext view = new ContextAndSubcontext();
            view.dispose();
        } else if (e.getSource() == view.getChatsButton()) {

//            ChatHistoryStudentView view = new ChatHistoryStudentView();
            view.dispose();
        } else if (e.getSource() == view.getProgressButton()) {

            try {
//                TeacherProgress view = new TeacherProgress(1);
                view.dispose();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

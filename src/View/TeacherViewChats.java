package View;

import Controllers.AppController;
import Database.Database;
import Models.*;
import config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/*
* @Author Oliver
*/

public class TeacherViewChats extends JFrame implements ActionListener {

    private JPanel MainPanel;
    private JPanel ContainerPanel;
    private JPanel HeaderPanel;
    private JButton homeButton;
    private JButton progressButton;
    private JButton learningButton;
    private JButton chatButton;
    private JPanel NavBarPanel;
    private JScrollPane bodyScrollPanel;
    private JPanel bodyPanel;
    private JLabel logoLabel;
    private JLabel teacherViewChatLabel;
    private JPanel textPanel;
    private JTextArea studentTextArea;
    private JPanel studentTextPanel;
    private JPanel partnerTextPanel;
    private JTextArea partnerTextArea;
    private JLabel studentNameLabel;
    private Config config;
    private AppController mainController;
    private LanguageDAO languageDAO;
    private Connection db;

    //ArrayLists defined.
    private ArrayList<String> studentDialogue = new ArrayList<>();
    private ArrayList<String> partnerDialogue = new ArrayList<>();
    private List<Speech> messages = new ArrayList<Speech>();

    private int counter = 0;
    private int turn = 0;
    private int line = 0;

    private int idConversation;
    private int userId;
    private int idSubcontext;
    private int idLanguage;
    private String difficulty;

    // int _userId, int _idSubcontext, int _idLanguage, String _difficulty
    public TeacherViewChats(AppController mainController, Integer idConversation) {
//        this.userId = _userId;
//        this.idSubcontext = _idSubcontext;
//        this.idLanguage = _idLanguage;
//        this.difficulty = _difficulty;
        this.idConversation = idConversation;
        this.mainController = mainController;
        this.config = mainController.getConfig();
        this.db = mainController.getDb();


        //Sets JFrame basics
        setTitle("Teacher View Chats Page");

        setSize(450, 800);
        setLocationRelativeTo(null);

        setContentPane(MainPanel);

        //Prevents resizing of the window
        setResizable(false);
        //Exit app on window close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Sets Logo
        //Icon iconLogo = new ImageIcon(new ImageIcon("src/resources/logo.jpeg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        //logoLabel.setIcon(iconLogo);
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        logoLabel.setIcon(iconLogo);


        //Formats lines for the textAreas
        studentTextArea.setLineWrap(true);
        studentTextArea.setWrapStyleWord(true);
        partnerTextArea.setLineWrap(true);
        partnerTextArea.setWrapStyleWord(true);

        //Sets name to userID
        studentNameLabel.setText(String.valueOf(userId));

        //config = new Config();
        //db = Database.getConnection(config.getProperty("db.name"));
        // Initialising LanguageDAO with connection to DB, later used to retrieve idLanguage and languageName.
        languageDAO = new LanguageDAO(db);

        //Allows the buttons to detect clicks
        homeButton.addActionListener(this);
        learningButton.addActionListener(this);
        chatButton.addActionListener(this);
        progressButton.addActionListener(this);

        //Shows JFrame
        setVisible(true);

        //Calls method.
        displayDialogue();

    }

    public void displayDialogue() {

        //Connects to the DAOs
        //ConversationDAO conversationDAO = new ConversationDAO(db);
        SpeechDAO speechDAO = new SpeechDAO(db);
        //SubContextDAO subContextDAO = new SubContextDAO(db);

        // pass in global class values that were passed in through class instructor (given from last frame, sub-context view)
        //idConversation = conversationDAO.getConversationId(idSubcontext, idLanguage, difficulty);
        messages = speechDAO.getMessagesByConversationId(idConversation);
        // Populating ArrayLists with appropriate dialogue for person 1 and person 2
        for (Speech speech_object : messages) {
            if (speech_object.getPerson() == 'A') {
                studentDialogue.add(speech_object.getMessage());
            } else {
                partnerDialogue.add(speech_object.getMessage());
            }
        }

        // Checks to add buffers to smaller dialogue lists that would otherwise throw an OutOfBounds error
        if (studentDialogue.size() != partnerDialogue.size()) {
            int difference = Math.abs(Math.max(studentDialogue.size(),partnerDialogue.size()) - Math.min(studentDialogue.size(),partnerDialogue.size()));
            if (studentDialogue.size() < partnerDialogue.size()) {
                for (int i = 0; i < difference;i++) studentDialogue.add("None");
            } else {
                for (int i = 0; i < difference;i++) partnerDialogue.add("None");
            }
        }

        //Determines the maximum lines between A and B dialogues.
        int maxLines = Math.max(studentDialogue.size(),partnerDialogue.size());

        //Iterates through the script to print each line until it reaches maxLines.
        for (int counter = 0; counter < maxLines; counter++) {

            //The studentArea's dialogue
            studentTextArea.append("- Line " + (counter+1) + "-\n");
            studentTextArea.append(studentDialogue.get(counter));
            studentTextArea.append("\n\n");
            //partnerArea's dialogue
            partnerTextArea.append("- Line " + (counter+1) + "-\n");
            partnerTextArea.append(partnerDialogue.get(counter));
            partnerTextArea.append("\n\n");
            //Iterate Counter
            counter++;
        }
    }

    //Gives the buttons functionality
    //@TODO Without going through login, button presses will cause a null error as data is not in the controller.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            dispose();
            mainController.navigateToHomePage();
        } else if (e.getSource() == learningButton) {
            dispose();
            //mainController.navigateToContextAndSubcontext;
        } else if (e.getSource() == chatButton) {
            dispose();
            mainController.navigateToTeacherProgressList();
        } else if (e.getSource() == progressButton) {
            dispose();
            mainController.navigateToTeacherProgressList();
        }
    }
}
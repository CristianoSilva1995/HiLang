package Models;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Model for Chat History page
 * @author Shiyan Bhandari, Arkadiusz Nowak
 */

public class ChatHistory {
    private Connection db;
    private Integer idUser;
    private DefaultListModel<String> listModel;
    private List<ConversationHistory> userHistory = new ArrayList<ConversationHistory>();
    private HashMap<Integer, Integer> conversationsList = new HashMap<>();
    private HashMap<Integer, Integer> subcontextsList = new HashMap<>();

    public ChatHistory(Integer idUser) {  // idUser of current authed User or any User if Teacher
        // Make sure a valid user id is passed
        if (idUser == null) {
            throw new NullPointerException();
        }
        this.idUser = idUser;
    }

    // Stores the referenced db
    public void setDb(Connection db) {
        // Make sure a valid db reference is passed
        if (db == null) {
            throw new NullPointerException();
        }
        this.db = db;
    }

    // Retrieve the list model
    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    // Set the list model
    public void setListModel(DefaultListModel<String> listModel) {
        this.listModel = listModel;
    }

    // Retrieves and stores in the list model a list of chats a user by the given id has participated in
    public void findChatsByUserId(Integer idUser) {
        setIdUser(idUser);
        ConversationHistoryDAO historyDAO = new ConversationHistoryDAO(db);
        userHistory = historyDAO.getUserHistory(idUser);

        setListModel(new DefaultListModel());

        String contextName;
        String subContextName;
        String languageName;
        String languageLevel;
        Integer convId;
        Integer subContextId;
        Integer idx = 0;
        conversationsList.clear();
        // Loop through conversation history, of each conversation, and retrieve details needed to be displayed later
        for (ConversationHistory historyItem: userHistory) {
            // Get conversation data
            ConversationDAO conversationDAO = new ConversationDAO(db);
            Conversation conv = conversationDAO.getConversation(historyItem.getIdConversation());

            // Save conversation id
            convId = historyItem.getIdConversation();
            subContextId = conv.getIdSubContext();

            // Get conversation language level
            languageLevel = conv.getLanguageLevel();

            // Get conversation language name
            languageName = new LanguageDAO(db).getLanguageName(conv.getIdLanguage());

            // Get context and subcontext of the conversation
            contextName = conversationDAO.getContextName(historyItem.getIdConversation());
            subContextName = conversationDAO.getSubContextName(historyItem.getIdConversation());

            // Add details needed to be displayed to the list model
            listModel.addElement(languageName + " ("+ languageLevel + ") - " + contextName + ", " + subContextName);
            // Store conversation ids for teacher
            conversationsList.put(idx, convId);
            // Store conversation ids for user
            subcontextsList.put(idx, subContextId);
            ++idx;
        }
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    // Get student's name by given idUser
    public String getStudentName() {
        return new UserDAO(db).getUserNameById(idUser);
    }

    public HashMap<Integer, Integer> getConversationsList() {
        return conversationsList;
    }

    public void setConversationsList(HashMap<Integer, Integer> conversationsList) {
        this.conversationsList = conversationsList;
    }

    public HashMap<Integer, Integer> getSubcontextsList() {
        return subcontextsList;
    }

    public void setSubcontextsList(HashMap<Integer, Integer> subcontextsList) {
        this.subcontextsList = subcontextsList;
    }
}
package Models;
/* AUTHOR: Jonathan Carrillo-Sanchez
 *  ID: w1758229 */

import java.sql.Connection;

public class ChatPageModel {
    private Connection db;
    private ConversationDAO conversationDAO;
    private ConversationHistoryDAO conversationHistoryDAO;
    private SubContextDAO subContextDAO;
    private SpeechDAO speechDAO;
    private UserDAO userDAO;
    // default constructor
    public ChatPageModel() {

    }
    // Sets the DB connection in order to use DAO classes for chat page.
    public void setDb(Connection db){
        this.db = db;
    }
    // All method below return DAO objects which are passed as a reference the connection to the SQLite DB
    public ConversationDAO getConversationDAO() {
        conversationDAO = new ConversationDAO(db);
        return conversationDAO;
    }
    // added model to retrieve user DAO in chat page controller
    public UserDAO getUserDAO() {
        userDAO = new UserDAO(db);
        return userDAO;
    }

    public ConversationHistoryDAO getConversationHistoryDAO() {
        conversationHistoryDAO = new ConversationHistoryDAO(db);
        return conversationHistoryDAO;
    }

    public SubContextDAO getSubContextDAO() {
        subContextDAO = new SubContextDAO(db);
        return subContextDAO;
    }

    public SpeechDAO getSpeechDAO() {
        speechDAO = new SpeechDAO(db);
        return speechDAO;
    }
}

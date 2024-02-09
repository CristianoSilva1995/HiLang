package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * ConversationDAO
 * @author Cristiano Silva, Arkadiusz Nowak
 */
public class ConversationDAO {
    private PreparedStatement preparedStatement;
    private Connection db;
    private ResultSet res;
    private Conversation conversation;

    public ConversationDAO (Connection db){
        // Make sure a valid db reference is passed
        if (db == null) {
            throw new NullPointerException();
        }
        this.db = db;
    }

    // Retrieves subContextName by the given Conversation ID
    public String getSubContextName(Integer conversationId) {
        String result = null;
        String query = "SELECT sc.subContextName FROM conversation conv " +
                "JOIN subcontext sc ON conv.idSubContext = sc.idSubContext WHERE conv.idConversation = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, conversationId);
            res = preparedStatement.executeQuery();
            while (res.next()){
                result = res.getString("subContextName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    // Retrieves contextName by the given Conversation ID
    public String getContextName(Integer conversationId) {
        String result = null;
        String query = "SELECT c.contextName FROM conversation conv " +
                "JOIN subcontext sc ON conv.idSubContext = sc.idSubContext " +
                "JOIN context c ON sc.idContext = c.idContext WHERE conv.idConversation = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, conversationId);
            res = preparedStatement.executeQuery();
            while (res.next()){
                result = res.getString("contextName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Conversation getConversation(Integer id) {
        String query = "SELECT idConversation, idSubContext, idLanguage, languageLevel, grammarStructure," +
                " vocabulary, dateTimeCreated FROM conversation WHERE idConversation = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            while (res.next()){
                conversation = new Conversation(res.getInt("idConversation"),
                        res.getInt("idSubContext"),
                        res.getInt("idLanguage"),
                        res.getString("languageLevel"),
                        res.getString("grammarStructure"),
                        res.getString("vocabulary"),
                        res.getString("dateTimeCreated"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conversation;
    }

    public String getDifficulty(Integer idSubContext){
        String getDifficulty = null;
        String query = "SELECT languageLevel FROM conversation WHERE idSubContext = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idSubContext);
            res = preparedStatement.executeQuery();
            while (res.next()){
                getDifficulty = res.getString("languageLevel");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getDifficulty;
    }

    // Method that returns a certain conversation id (int) given the sub-context id,language id and language level.
    // This is so that this conversation can be accessed and the speech messages can be displayed.
    public int getConversationId(int idSubContext,int idLanguage,String languageLevel){
        int getConvId = -1;
        String query = "SELECT idConversation FROM conversation WHERE idSubContext = ? AND idLanguage = ? AND languageLevel = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idSubContext);
            preparedStatement.setInt(2, idLanguage);
            preparedStatement.setString(3, languageLevel);
            res = preparedStatement.executeQuery();
            while (res.next()){
                getConvId = res.getInt("idConversation");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getConvId;
    }

    //@TODO getSpeech from person (A and B) by conversationID
    //
}

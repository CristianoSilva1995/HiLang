package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SpeechDAO for speech db table
 * @author Arkadiusz Nowak, Johnny
 */

public class SpeechDAO {
    private Connection db;
    private Statement stmt;
    private ResultSet res;
    private List<Speech> messages = new ArrayList<Speech>();

    //johny: Added SpeechDAO constructor to accept in database connection
    public SpeechDAO(Connection db) {
        // Make sure a valid db reference is passed
        if (db == null) {
            throw new NullPointerException();
        }
        this.db = db;
    }

    // Retrieve all messages by given conversation id
    public List<Speech> getMessagesByConversationId(Integer convId) {
        String query = "SELECT * FROM speech WHERE idConversation = ? ORDER BY idSpeech";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, convId);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                //contexts.add(new Context(res.getInt("idContext"), res.getString("contextName")));
                Speech row = new Speech(res.getInt("idSpeech"),
                        res.getInt("idConversation"),
                        res.getString("person").charAt(0),
                        res.getString("message"),
                        res.getString("translation"));
                messages.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messages;
    }
}
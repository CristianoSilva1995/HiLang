package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * ConversationHistoryDAO
 * Methods to create or retrieve data from DB
 * @author Cristiano Silva, Arkadiusz Nowak, Johnathan
 */
public class ConversationHistoryDAO {
    private PreparedStatement preparedStatement;
    private Connection db;
    private ResultSet res;

    public ConversationHistoryDAO(Connection db) {
        this.db = db;
    }



    public ConversationHistory getTimesAccessedAndCompleted(Integer idUser, Integer idConversation){
        ConversationHistory conversationHistory = null;
        String query = "SELECT timesAccessed, completed FROM conversationHistory WHERE idUser = ? AND idConversation = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, idConversation);
            res = preparedStatement.executeQuery();
            while (res.next()){
                conversationHistory = new ConversationHistory(res.getInt("timesAccessed"),res.getBoolean("completed"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conversationHistory;
    }

    public Boolean createConversationHistory(Integer idUser, Integer idConversation, Integer timesAccess, Boolean completed){
        String query = "INSERT INTO conversationHistory(idUser, idSecondUser, idConversation, dateTimeFirstAccessed, dateTimeLastAccessed, timesAccessed, completed)\n" +
                "VALUES (?,?,?,?,?,?,?)";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, idConversation);
            preparedStatement.setString(4, " ");
            preparedStatement.setString(5, " ");
            preparedStatement.setInt(6, timesAccess);
            preparedStatement.setBoolean(7, completed);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public Integer getIDConversation(Integer idUser, Integer idSubContext){

        String query = "SELECT conversationHistory.idConversation FROM conversationHistory JOIN conversation c ON conversationHistory.idConversation = c.idConversation " +
                "WHERE conversationHistory.idUser = ? AND c.idSubContext = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, idSubContext);
            res = preparedStatement.executeQuery();
            return res.getInt("idConversation");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<ConversationHistory> getUserHistory (Integer idUser) {
        List<ConversationHistory> history = new ArrayList<>();

        String query = "SELECT idSecondUser, idConversation, dateTimeFirstAccessed, dateTimeLastAccessed," +
                " timesAccessed, completed FROM conversationHistory WHERE idUser = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            res = preparedStatement.executeQuery();
            while (res.next()){
                history.add(new ConversationHistory(
                        idUser,
                        res.getInt("idSecondUser"),
                        res.getInt("idConversation"),
                        res.getString("dateTimeFirstAccessed"),
                        res.getString("dateTimeLastAccessed"),
                        res.getInt("timesAccessed"),
                        res.getBoolean("completed")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return history;
    }
}

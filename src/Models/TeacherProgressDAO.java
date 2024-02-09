package Models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * TeacherProgressDAO
 * @author Cristiano Silva
 */
public class TeacherProgressDAO {
    private Connection db;
    private ResultSet res;
    private PreparedStatement preparedStatement;

    public TeacherProgressDAO(Connection db){ this.db = db; }

    public List<SubContext> getSubContextConversationHistory(Integer idUser){
        List<SubContext> subContextList = new ArrayList<>();
        String query = "SELECT s.idSubContext, s.subContextName FROM conversation " +
                "JOIN conversationHistory cH on conversation.idConversation = cH.idConversation\n" +
                "JOIN subcontext s on conversation.idSubContext = s.idSubContext " +
                "WHERE cH.idUser = ?\n";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1,idUser);
            res = preparedStatement.executeQuery();
            while (res.next()){
                subContextList.add(new SubContext(res.getInt("idSubContext"), res.getString("subContextName")));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subContextList;
    }
}

package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * @Author Oliver, Cristiano
 */
public class StudentProgressDAO {
    private Connection db;
    private ResultSet res;
    private PreparedStatement preparedStatement;

    //Databse connection
    public StudentProgressDAO(Connection db){
        this.db = db;
    }

    //
    public List<SubContext> getSubContextConversationHistory(Integer idUser){
        //Define ArrayList
        List<SubContext> subContextList = new ArrayList<>();

        //Query searches through DB to find the subtopics the student has attempted
        String query = "SELECT s.idSubContext, s.subContextName FROM conversation " +
                "JOIN conversationHistory cH on conversation.idConversation = cH.idConversation\n" +
                "JOIN subcontext s on conversation.idSubContext = s.idSubContext " +
                "WHERE cH.idUser = ?\n";
        try {
            //Executes Query
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1,idUser);
            res = preparedStatement.executeQuery();
            //Adds to ArrayList
            while (res.next()){
                subContextList.add(new SubContext(res.getInt("idSubContext"), res.getString("subContextName")));
            }
        //Exception Catch
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subContextList;
    }
}
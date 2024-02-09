package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubContextDAO {

    private Connection db;
    private Statement stmt;
    private ResultSet rs;

    public SubContextDAO(Connection db){ this.db = db; }

    // This method is passed to the model and contains the result of the sql query.
    // A List method of type context class that does not take any parameter.
    // The method returns a list of all contexts contained in the context table.

    public  List <Context> getContextList() {
        List<Context> contextList = new ArrayList<>();
        try {
            String query = "SELECT idContext,contextName FROM context ORDER BY contextName";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Context subCon = new Context(rs.getInt("idContext"),rs.getString("contextName"));
                contextList.add(subCon);
            }
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        return contextList;
    }

    // This method is passed to the model and contains the result of the sql query.
    // A List method of type SubContext class that does not take any parameter.
    // The method returns a list of all subContexts contained in the subcontext table.
    public  List <SubContext> getSubContextList() {
        List<SubContext> subContextList = new ArrayList<>();
        try {
            String query = "SELECT idSubContext, idContext, subContextName FROM subcontext;";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                SubContext subCon = new SubContext(rs.getInt("idContext"),rs.getString("subContextName"));
                subContextList.add(subCon);
            }
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        return subContextList;
    }

    // This method is passed to the model and contains the result of the sql query.
    // Integer method getContextId takes a string parameter.
    // The methods return the id number of the specific contextname.
    public Integer getContextId(String name) {
        try {
            String query = "SELECT idContext FROM context WHERE contextName = ?";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return rs.getInt("idContext");
            }
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return 0;
    }

    //Retrieves the id of the subContext
    public Integer getSubContextId(String name) {
        try {
            String query = "SELECT idSubContext FROM subcontext WHERE subContextName = ?";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return rs.getInt("idSubContext");
            }
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return 0;
    }

    // This method is passed to the model and contains the result of the sql query.
    // A List method of type SubContext class which takes an int parameter.
    // This method returns a list of all sub contents that have the same contextId.
    // Subcontext table is a child of a Context table and therefore contextId is a foreign key in the subcontext table.
    // We are using the foreign key to fetch all subcontexts that reference that primary key passed by the contextName.
    public  List <SubContext> getSubContextListByID( int id) {

        List <SubContext> subContextList = new ArrayList<>();
        try {
            String query = "SELECT idSubContext,subContextName FROM subcontext WHERE idContext = ?";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                SubContext subCon = new SubContext(rs.getInt("idSubContext"),rs.getString("subContextName"));
                subContextList.add(subCon);
            }
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return subContextList;
    }

    //Retrieves the id of the context passing the name of the SubContext
    public  Integer getContextIDBySubContextName( String subContextName) {
        try {
            String query = "SELECT idContext FROM subcontext WHERE subContextName = ?";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, subContextName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return rs.getInt("idContext");
            }
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return 0;
    }

    // returns subcontext name
    public String getSubContextName(int id) {

        String getSubContName = "" ;
        try {
            String query = "SELECT subContextName FROM subcontext WHERE idSubContext = ?";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                getSubContName = res.getString("subContextName");
            }
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return getSubContName;
    }

    //retrieves the total number of subtopics on the DB
    public Integer getSubContextCount() {
        Integer length;
        try {
            String query = "SELECT COUNT(subContextName) as row_count FROM subcontext";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            ResultSet res = preparedStatement.executeQuery();
            return res.getInt("row_count");
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return 1;
    }
    //retrieves count of the topics which were completed by the user
    public Integer getSubContextCountCompleted(Integer idUser) {
        try {
            String query = "SELECT COUNT(subContextName) as row_count FROM subcontext JOIN conversation c on subcontext.idSubContext = c.idSubContext\n" +
                    "JOIN conversationHistory cH on c.idConversation = cH.idConversation\n" +
                    "WHERE  cH.completed = 1 AND idUser = ?";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            ResultSet res = preparedStatement.executeQuery();
            return res.getInt("row_count");
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return 1;
    }
}
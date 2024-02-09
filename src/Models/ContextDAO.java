package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ContextDAO for Context db table
 * @author Arkadiusz Nowak, Cristiano Silva
 */

public class ContextDAO {
    private Connection db;
    private Statement stmt;

    private PreparedStatement preparedStatement;
    private ResultSet res;
    private List<Context> contexts = new ArrayList<Context>();

    public ContextDAO(Connection db) {
        // Make sure a valid db reference is passed
        if (db == null) {
            throw new NullPointerException();
        }
        this.db = db;
    }

    // Retrieves a list of contexts, sorted alphabetically
    public List<Context> getAll() {
        String query = "SELECT idContext, contextName FROM context ORDER BY contextName";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                contexts.add(new Context(res.getInt("idContext"), res.getString("contextName")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contexts;
    }

    // Retrieve context data by given Id
    public Context getById(Integer id) {
        Context context = null;
        String query = "SELECT idContext, contextName FROM context WHERE idContext = ?";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                context = new Context(res.getInt("idContext"), res.getString("contextName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return context;
    }

    // Retrieve context name by given id
    public String getContextNameById(Integer id) {
        try {
            String query = ("SELECT contextName FROM context WHERE idContext = ?");
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                return res.getString("contextName");
            }
        } catch (SQLException e) {
            System.out.println("SQL threw error");
        }

        return "";
    }

    // Retrieve context by given context name
    public Context getByName(String name) {
        Context context = null;
        String query = "SELECT idContext, contextName FROM context WHERE contextName = ?";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                context = new Context(res.getInt("idContext"), res.getString("contextName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return context;
    }

    public Integer getContextID(String subContext){
        Integer idContext = 0;
        String query = "SELECT DISTINCT idContext FROM subcontext WHERE subContextName = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, subContext);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                idContext = res.getInt("idContext");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idContext;
    }

    public Integer getSubContextID(String subContext){
        Integer idSubContext = 0;
        String query = "SELECT DISTINCT idSubContext FROM subcontext WHERE subContextName = ?";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, subContext);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                idSubContext = res.getInt("idSubContext");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idSubContext;
    }
}
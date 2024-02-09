package Models;

/**
 * Model for Context db table
 * @author Arkadiusz Nowak
 */

public class Context {

    private int idContext;
    private String contextName;

    Context(int id, String name){
        this.contextName = name;
        this.idContext = id;
    }

    public int getIdContext() {
        return idContext;
    }

    public String getContextName() {
        return contextName;
    }
}
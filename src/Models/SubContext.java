package Models;

import java.net.http.HttpConnectTimeoutException;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class SubContext {
    //handles all the logic  for  presents it to the controller
    private int idContext;
    private int idSubContext;
    private String subContextName;
    private Connection db;
    public SubContextDAO subContextDetails;

    public Connection getDb() {
        return db;
    }

    public void setDb(Connection db) {
        this.db = db;
    }

    public SubContext() {};


    // A constructor  that contains the int and String parameter.
    public SubContext( int idSubContext, String subContextName) {
        this.idSubContext = idSubContext;
        this.subContextName = subContextName;

    }
    // Integer method getContextId takes a string parameter.
    // The methods return the id number of the specific contextname.
    public Integer getContextId (String name) {
        subContextDetails= new SubContextDAO(db);
        return subContextDetails.getContextId(name);
    }
    // A List method of type context class that does not take any parameter.
    // The method returns a list of all contexts contained in the context table.
    // Calls the SubContextDAO object to retrieve the getContextList method.
    public List <Context> getContextList () {
        subContextDetails= new SubContextDAO(db);
        return subContextDetails.getContextList();
    }
    // A List method of type SubContext class which takes a int parameter.
    // This method returns a list of all sub contents that have the same contextId.
    // Subcontext table is a child of a Context table and therefore contextId is a foreign key in the subcontext table.
    // We are using the foreign key to fetch all subcontexts that reference that primary key passed by the contextName.
    public List <SubContext> getSubContextListByID (int id) {
        subContextDetails= new SubContextDAO(db);
        return subContextDetails.getSubContextListByID(id);
    }

    // Integer method getSubContextId takes a string parameter.
    // The methods return the id number of the specific subContextName.
    public Integer getSubContextId(String name) {
        subContextDetails= new SubContextDAO(db);
        return subContextDetails.getSubContextId(name);
    }

    @Override
    public String toString() {
        return "SubContext{" +
                "idContext=" + idContext +
                ", idSubContext=" + idSubContext +
                ", subContextName='" + subContextName + '\'' +
                '}';
    }
    public int getIdContext() {
        return idContext;
    }

    public void setIdContext(int idContext) {
        this.idContext = idContext;
    }

    public int getIdSubContext() {
        return idSubContext;
    }

    public void setIdSubContext(int idSubContext) {
        this.idSubContext = idSubContext;
    }

    public String getSubContextName() {
        return subContextName;
    }
    public void setSubContextName(String subContextName) {
        this.subContextName = subContextName;
    }}
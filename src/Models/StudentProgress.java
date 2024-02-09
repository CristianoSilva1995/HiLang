package Models;

import Controllers.AppController;
import config.Config;

import java.sql.Connection;
import java.util.List;

/*
 * @Author Oliver, Cristiano
 */
public class StudentProgress {
    private Connection db;
    private SubContextDAO subContextDAO;

    private ConversationHistoryDAO conversationHistoryDAO;
    private String contextName;
    private String subContextName;
    private String difficulty;
    private Boolean completed;

    public StudentProgress(){

    }

    public StudentProgress(String contextName, String subContextName, String difficulty, Boolean completed, Integer timesCompleted) {
        this.contextName = contextName;
        this.subContextName = subContextName;
        this.difficulty = difficulty;
        this.completed = completed;
        this.timesCompleted = timesCompleted;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getSubContextName() {
        return subContextName;
    }

    public void setSubContextName(String subContextName) {
        this.subContextName = subContextName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getTimesCompleted() {
        return timesCompleted;
    }

    public void setTimesCompleted(Integer timesCompleted) {
        this.timesCompleted = timesCompleted;
    }

    private Integer timesCompleted;

    //sets the Connection
    public void setDb(Connection db) {
        this.db = db;
    }

    //retrieves a List of subContext from the subContextDAO
    public List<SubContext> getSubContext(Integer idUser){
        StudentProgressDAO teacherProgressDAO= new StudentProgressDAO(db);
        return teacherProgressDAO.getSubContextConversationHistory(idUser);
    }
    //retrieves the context name from the contextDAO
    //Uses subContextDAO to retrieve the contextID matching the chosen SubContextName
    //Calls contextDAO to retrieve the name attached to the ID previously described
    public String getContextNameDB(String subContextName){
        ContextDAO contextDAO = new ContextDAO(db);
        SubContextDAO subContextDAO = new SubContextDAO(db);
        Integer contextID = subContextDAO.getContextIDBySubContextName(subContextName);
        return contextDAO.getContextNameById(contextID);
    }

    //retrieves the difficulty of the chosen subContext
    //gets the idSubContext from the subContextDAO
    public String getDifficultyDB(String contextName){
        SubContextDAO subContextDAO = new SubContextDAO(db);
        Integer idSubContext = subContextDAO.getSubContextId(contextName);
        ConversationDAO conversationDAO = new ConversationDAO(db);
        return conversationDAO.getDifficulty(idSubContext);
    }
    //retrieves the number of times a topic was done and if it is completed or not
    public ConversationHistory getTimesAccessedAndCompleted(Integer idUser, Integer idConversation){
        ConversationHistoryDAO conversationHistoryDAO = new ConversationHistoryDAO(db);
        return conversationHistoryDAO.getTimesAccessedAndCompleted(idUser,idConversation);
    }
    //gets the username where the idUser matches the one passed
    public String getUsername(Integer idUser){
        UserDAO userDAO = new UserDAO(db);
        return userDAO.getUserNameById(idUser);
    }
    //retrieves all subtopics found on the ConversationHistory
    public List<SubContext> getAllSubContextFromConversationHistory(Integer idUser){
        StudentProgressDAO teacherProgressDAO = new StudentProgressDAO(db);
        return teacherProgressDAO.getSubContextConversationHistory(idUser);

    }

    //retrieves the number of topics available on the subTopic table
    public Integer getCountTotalSubContext(){
        subContextDAO = new SubContextDAO(db);
        return subContextDAO.getSubContextCount();
    }
    //retrieves the number of subtopics completed by the user and calls subContextDAO method.
    public Integer getCountTotalCompletedSubContext(Integer idUser){
        subContextDAO = new SubContextDAO(db);
        return subContextDAO.getSubContextCountCompleted(idUser);
    }

    //retrieves the id of the conversation
    public Integer getIdConversation(Integer idUser, Integer idSubContext){
        conversationHistoryDAO = new ConversationHistoryDAO(db);
        return conversationHistoryDAO.getIDConversation(idUser, idSubContext);
    }

    public Integer getSubContextID(String subContextName){
        return subContextDAO.getSubContextId(subContextName);
    }
}

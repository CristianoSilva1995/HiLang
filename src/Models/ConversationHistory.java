package Models;

import java.sql.Connection;
/**
 * ConversationHistory model
 * @author Cristiano Silva, Arkadiusz Nowak, Johnathan
 */
public class ConversationHistory {
    private Integer idUser;
    private Integer idSecondUser;
    private Integer idConversation;
    private String dateTimeFirstAccessed;
    private String dateTimeLastAccessed;
    private Integer timesAccessed;
    private Boolean completed;

    private Connection db;

    public ConversationHistory(Integer idUser, Integer idSecondUser, Integer idConversation, String dateTimeFirstAccessed, String dateTimeLastAccessed, Integer timesAccessed, Boolean completed) {
        this.idUser = idUser;
        this.idSecondUser = idSecondUser;
        this.idConversation = idConversation;
        this.dateTimeFirstAccessed = dateTimeFirstAccessed;
        this.dateTimeLastAccessed = dateTimeLastAccessed;
        this.timesAccessed = timesAccessed;
        this.completed = completed;
    }

    public ConversationHistory(Integer timesAccessed, Boolean completed){
        this.timesAccessed = timesAccessed;
        this.completed = completed;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getIdSecondUser() {
        return idSecondUser;
    }

    public Integer getIdConversation() {
        return idConversation;
    }

    public String getDateTimeFirstAccessed() {
        return dateTimeFirstAccessed;
    }

    public String getDateTimeLastAccessed() {
        return dateTimeLastAccessed;
    }

    public Integer getTimesAccessed() {
        return timesAccessed;
    }

    public String getCompleted() {
        String answer;
        if(completed){
            answer = "Yes";
        }else{
            answer = "No";
        }
        return answer;
    }
    public Boolean getCompletedBoolean() {
        return completed;
    }


    public void setDb(Connection db){
        this.db = db;
    }

    public void createConversationHistory(){
        ConversationHistoryDAO conversationHistoryDAO = new ConversationHistoryDAO(db);
        conversationHistoryDAO.createConversationHistory(getIdUser(), getIdConversation(),getTimesAccessed(), getCompletedBoolean());
    }

}

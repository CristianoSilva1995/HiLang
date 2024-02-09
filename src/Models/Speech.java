package Models;

/**
 * Model for Speech db table
 * @author Arkadiusz Nowak
 */

public class Speech {
    private int idSpeech;
    private int idConversation;
    private char person;
    private String message;
    private String translation;

    public Speech(int idSpeech, int idConversation, char person, String message, String translation) {
        this.idSpeech = idSpeech;
        this.idConversation = idConversation;
        this.person = person;
        this.message = message;
        this.translation = translation;
    }

    public int getIdSpeech() {
        return idSpeech;
    }

    public void setIdSpeech(int idSpeech) {
        this.idSpeech = idSpeech;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public char getPerson() {
        return person;
    }

    public void setPerson(char person) {
        this.person = person;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
